package com.example.DrinkItUp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    // ตัวแปรที่ใช้เก็บข้อมูลของปุ่มต่าง ๆ
    private Button btnNormal1, btnNormal2, btnNormal3, btnNormal4, btnNormal5, btnNormal6;
    private Button btnHard1, btnHard2, btnHard3, btnHard4, btnHard5, btnHard6, btnHard7, btnHard8, btnHard9;
    private TextView timerTextView, recipeName, recipeHint;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private boolean isPaused = false;  // ตัวแปรบ่งบอกว่าเกมถูกหยุดหรือไม่

    // ตัวแปรสำหรับ SoundPool
    private SoundPool soundPool;
    private int correctSoundId;
    private int incorrectSoundId;
    private int clickSoundId;
    private int timesUpSoundId;

    // ตัวแปรสำหรับเก็บส่วนผสมทั้งหมด และส่วนผสมของสูตรที่เลือก
    private final ArrayList<Integer> allIngredients = new ArrayList<>();
    private final List<Integer> currentRecipeImages = new ArrayList<>();
    private int correctClicks = 0; // ตัวนับการคลิกที่ถูกต้อง
    private int score = 0; // ตัวแปรสำหรับเก็บคะแนน

    // จับเวลาเริ่มต้น (2 นาที)
    private static final long START_TIME_IN_MILLIS = 120000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    // ตัวแปร backing sound
    private MediaPlayer backingSoundPlayer;

    // ตัวแปรนับจำนวนเกมที่เล่นแล้ว
    private int gameCount = 0;

    private boolean isPauseDialogShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // หา LinearLayout ในหน้า GameActivity
        LinearLayout gameLayout = findViewById(R.id.gameLayout);

        // โหลดสถานะ Dark Mode จาก SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);

        // ตั้งค่าพื้นหลังตามโหมดที่เลือก
        gameLayout.setBackgroundColor(ContextCompat.getColor(this,
                isDarkMode ? android.R.color.black : android.R.color.white));

        // ตั้งค่า MediaPlayer สำหรับเพลงประกอบ
        backingSoundPlayer = MediaPlayer.create(this, R.raw.backing_sound);
        backingSoundPlayer.setVolume(0.2f, 0.2f); // กำหนดความดังของเสียง
        backingSoundPlayer.setLooping(true);
        backingSoundPlayer.start();

        // สร้าง SoundPool และกำหนด AudioAttributes
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();

        // โหลดเสียงต่าง ๆ
        correctSoundId = soundPool.load(this, R.raw.correct_sound, 1);
        incorrectSoundId = soundPool.load(this, R.raw.incorrect_sound, 1);
        clickSoundId = soundPool.load(this, R.raw.click_sound, 1);
        timesUpSoundId = soundPool.load(this, R.raw.times_up_sound, 1);

        ImageButton pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setImageResource(R.drawable.button_pause);
        pauseButton.setOnClickListener(v -> showCustomPauseDialog());

        // เรียกฟังก์ชันเพื่อเพิ่มส่วนผสมทั้งหมดในลิสต์
        initializeAllIngredients();

        // เชื่อมโยงตัวแปรกับ View
        timerTextView = findViewById(R.id.timerTextView);
        progressBar = findViewById(R.id.progressBar);
        recipeName = findViewById(R.id.recipeName);
        recipeHint = findViewById(R.id.recipeHint);

        // ปุ่มโหมด Normal
        btnNormal1 = findViewById(R.id.btnNormal1);
        btnNormal2 = findViewById(R.id.btnNormal2);
        btnNormal3 = findViewById(R.id.btnNormal3);
        btnNormal4 = findViewById(R.id.btnNormal4);
        btnNormal5 = findViewById(R.id.btnNormal5);
        btnNormal6 = findViewById(R.id.btnNormal6);

        // ปุ่มโหมด Hard
        btnHard1 = findViewById(R.id.btnHard1);
        btnHard2 = findViewById(R.id.btnHard2);
        btnHard3 = findViewById(R.id.btnHard3);
        btnHard4 = findViewById(R.id.btnHard4);
        btnHard5 = findViewById(R.id.btnHard5);
        btnHard6 = findViewById(R.id.btnHard6);
        btnHard7 = findViewById(R.id.btnHard7);
        btnHard8 = findViewById(R.id.btnHard8);
        btnHard9 = findViewById(R.id.btnHard9);

        // เริ่มเกมใหม่และตั้งเวลา
        nextGame();
        startTimer();

        // ใช้ OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (!isPaused) {
                    showCustomPauseDialog();
                } else {
                    finish(); // ปิด Activity เมื่อเกมหยุดพัก
                }
            }
        });
    }


    // เพิ่มส่วนผสมทั้งหมดในลิสต์ allIngredients
    private void initializeAllIngredients() {
        allIngredients.add(R.drawable.apple);
        allIngredients.add(R.drawable.blue_berries);
        allIngredients.add(R.drawable.chili);
        allIngredients.add(R.drawable.chocolate);
        allIngredients.add(R.drawable.cocktail);
        allIngredients.add(R.drawable.coconut);
        allIngredients.add(R.drawable.coffee);
        allIngredients.add(R.drawable.grapes);
        allIngredients.add(R.drawable.green_tea);
        allIngredients.add(R.drawable.honey);
        allIngredients.add(R.drawable.ice);
        allIngredients.add(R.drawable.ice_cream);
        allIngredients.add(R.drawable.juices);
        allIngredients.add(R.drawable.lemon);
        allIngredients.add(R.drawable.mango);
        allIngredients.add(R.drawable.milk);
        allIngredients.add(R.drawable.mint);
        allIngredients.add(R.drawable.orange);
        allIngredients.add(R.drawable.peach);
        allIngredients.add(R.drawable.pineapple);
        allIngredients.add(R.drawable.strawberry);
    }

    // ตัวแปรเก็บสถานะโหมดเกมก่อนหน้า
    private boolean previousIsHardRecipe = false;

    private void nextGame() {
        // รีเซ็ตตัวนับการคลิกที่ถูกต้อง
        correctClicks = 0;

        // ถ้าเกมก่อนหน้าไม่ใช่เกมแรก อัปเดตคะแนนจากโหมดของเกมก่อนหน้า
        if (gameCount > 0) {
            updateScore(previousIsHardRecipe); // ใช้ค่าโหมดจากเกมก่อนหน้า
        } else {
            score = 0; // รีเซ็ตคะแนนถ้าเป็นเกมแรก
        }

        // กำหนดว่าเกมนี้จะเล่นในโหมด Normal หรือ Hard
        boolean isHardRecipe;
        if (gameCount < 3) {
            // 3 เกมแรกจะเล่นในโหมด Normal
            isHardRecipe = false;
        } else {
            // หลังจากเกมที่ 3 เป็นต้นไปจะผสม 70% Normal และ 30% Hard
            isHardRecipe = Math.random() < 0.3; // 30% โอกาสที่จะเป็นโหมดยาก (Hard)
        }

        // เพิ่มตัวนับเกม
        gameCount++;

        // สุ่มเลือกสูตรเครื่องดื่มจาก RecipeHelper
        Recipe randomRecipe = RecipeHelper.getRandomRecipe(this, isHardRecipe);

        // รีเซ็ตปุ่มทั้งหมดก่อนการเริ่มข้อใหม่
        resetButtons();

        // อัปเดตลิสต์ส่วนผสมที่ต้องการในสูตรปัจจุบัน
        currentRecipeImages.clear();
        currentRecipeImages.addAll(randomRecipe.getImageIds());

        // อัปเดต UI ที่เกี่ยวข้อง
        recipeName.setText(randomRecipe.getName());

        // ใช้ Recipe_TextFormatter เพื่อจัดรูปแบบคำอธิบาย
        recipeHint.setText(Recipe_TextFormatter.getFormattedDescription(this, randomRecipe.getDescription()));

        // ตั้งโหมดเกม
        if (!isHardRecipe) {
            setupNormalMode(randomRecipe);
        } else {
            setupHardMode(randomRecipe);
        }

        // เก็บค่าโหมดปัจจุบันไว้ใช้สำหรับรอบถัดไป
        previousIsHardRecipe = isHardRecipe;
    }


    // อัปเดตคะแนนและแสดงผลบนหน้าจอ
    private void updateScore(boolean isHardRecipe) {
        if (!isHardRecipe) {
            score += 200; // เพิ่มคะแนน 200 สำหรับโหมด Normal
        } else {
            score += 500; // เพิ่มคะแนน 500 สำหรับโหมด Hard
        }
        // อัปเดตคะแนนที่ TextView (แปลงเป็น String)
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(String.valueOf(score));
    }

    // รีเซ็ตสถานะของปุ่มทั้งหมด
    private void resetButtons() {
        // รีเซ็ตปุ่มทั้งหมด
        Button[] allButtons = new Button[] {
                btnNormal1, btnNormal2, btnNormal3, btnNormal4, btnNormal5, btnNormal6,
                btnHard1, btnHard2, btnHard3, btnHard4, btnHard5, btnHard6, btnHard7, btnHard8, btnHard9
        };

        for (Button button : allButtons) {
            button.setEnabled(true);
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); // ลบเครื่องหมายผิด/ถูก
        }
    }

    // ปรับขนาด drawable
    private void resizeButtonDrawables(Button[] buttons) {
        for (Button button : buttons) {
            if (button != null) {
                Drawable[] drawables = button.getCompoundDrawables(); // [left, top, right, bottom]
                Drawable[] newDrawables = new Drawable[4];

                for (int i = 0; i < drawables.length; i++) {
                    if (drawables[i] != null) {
                        // แปลง dp เป็น px
                        int widthInPx = (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                75,
                                button.getContext().getResources().getDisplayMetrics()
                        );
                        int heightInPx = (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                75,
                                button.getContext().getResources().getDisplayMetrics()
                        );

                        // สร้าง Drawable ใหม่ด้วยขนาดที่กำหนด
                        drawables[i].setBounds(0, 0, widthInPx, heightInPx);
                        newDrawables[i] = drawables[i];
                    } else {
                        newDrawables[i] = null; // หากไม่มี Drawable ใส่ค่า null
                    }
                }

                // ตั้งค่า Drawable ใหม่ให้กับปุ่ม
                button.setCompoundDrawables(newDrawables[0], newDrawables[1], newDrawables[2], newDrawables[3]);
            }
        }
    }


    // ตั้งค่าปุ่มในโหมด Normal
    private void setupNormalMode(Recipe recipe) {
        findViewById(R.id.normalLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.hardLayout).setVisibility(View.GONE);

        // สุ่มเลือกรูปภาพที่จะใช้ในปุ่ม (แสดง 6 รูปใน Normal)
        ArrayList<Integer> displayImages = generateDisplayImages(new ArrayList<>(recipe.getImageIds()), 6);

        // ตั้งค่าภาพและ Listener ให้กับปุ่ม
        Button[] buttons = new Button[] { btnNormal1, btnNormal2, btnNormal3, btnNormal4, btnNormal5, btnNormal6 };
        setButtonImagesAndListeners(buttons, displayImages);

        // ปรับขนาด Drawable บนปุ่มเป็น 75 x 75 dp
        resizeButtonDrawables(buttons);
    }

    // ตั้งค่าปุ่มในโหมด Hard
    private void setupHardMode(Recipe recipe) {
        findViewById(R.id.normalLayout).setVisibility(View.GONE);
        findViewById(R.id.hardLayout).setVisibility(View.VISIBLE);

        // สุ่มเลือกรูปภาพที่จะใช้ในปุ่ม (แสดง 9 รูปใน Hard)
        ArrayList<Integer> displayImages = generateDisplayImages(new ArrayList<>(recipe.getImageIds()), 9);

        // ตั้งค่าภาพและ Listener ให้กับปุ่ม
        Button[] buttons = new Button[] { btnHard1, btnHard2, btnHard3, btnHard4, btnHard5, btnHard6, btnHard7, btnHard8, btnHard9 };
        setButtonImagesAndListeners(buttons, displayImages);

        // ปรับขนาด Drawable บนปุ่มเป็น 75 x 75 dp
        resizeButtonDrawables(buttons);
    }

    // สุ่มเลือกรูปภาพที่จะแสดงในปุ่ม
    private ArrayList<Integer> generateDisplayImages(ArrayList<Integer> recipeImages, int totalButtons) {
        ArrayList<Integer> displayImages = new ArrayList<>(recipeImages);

        // ใช้ allIngredients เพื่อเพิ่มภาพเพิ่มเติมจนกว่าจะครบจำนวนปุ่ม
        Collections.shuffle(allIngredients);  // สุ่มภาพทั้งหมดใน allIngredients
        int index = 0;

        // เพิ่มภาพจาก allIngredients จนกระทั่งจำนวนของ displayImages เท่ากับ totalButtons
        while (displayImages.size() < totalButtons) {
            int candidate = allIngredients.get(index);
            // ตรวจสอบว่า candidate ไม่ซ้ำกับภาพที่มีใน displayImages
            if (!displayImages.contains(candidate)) {
                displayImages.add(candidate);
            }
            index++;
        }

        // สุ่มเรียงภาพ
        Collections.shuffle(displayImages);
        return displayImages;
    }

    // ตั้งค่าภาพและ Listener ให้กับปุ่ม
    private void setButtonImagesAndListeners(Button[] buttons, ArrayList<Integer> images) {
        for (int i = 0; i < buttons.length; i++) {
            final Button button = buttons[i];
            final Integer image = images.get(i);

            // ตั้งค่าภาพของปุ่ม
            button.setCompoundDrawablesWithIntrinsicBounds(0, image, 0, 0);

            // ตั้งค่า Listener ให้กับปุ่ม
            button.setOnClickListener(v -> handleButtonClick(button, image));
        }
    }

    private void handleButtonClick(Button button, int imageId) {
        // แปลง imageId เป็น Drawable
        Drawable imageDrawable = ContextCompat.getDrawable(this, imageId);

        // สร้าง icon สำหรับถูก/ผิด
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); // เรียกใช้การสั่น

        Drawable icon; // No need to initialize to null here

        if (currentRecipeImages.contains(imageId)) {
            correctClicks++;
            button.setEnabled(false); // Disable the button after correct click
            icon = ContextCompat.getDrawable(this, R.drawable.correct);

            // เพิ่มเวลา 5 วินาทีเมื่อคำตอบถูก
            timeLeftInMillis += 5000;
            if (timeLeftInMillis > 120000) {
                timeLeftInMillis = 120000;
            }

            // อัปเดตแสดงผลเวลาใหม่
            updateTimeDisplay();

            // หยุด Timer ที่กำลังทำงานอยู่
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            // เริ่ม Timer ใหม่
            startTimer();

            // เล่นเสียงเมื่อคำตอบถูก
            soundPool.play(correctSoundId, 1f, 1f, 0, 0, 1f);
        } else {
            button.setEnabled(false);
            icon = ContextCompat.getDrawable(this, R.drawable.incorrect);

            // ลดเวลา 20 วินาทีเมื่อคำตอบผิด
            timeLeftInMillis -= 20000;
            if (timeLeftInMillis < 0) {
                timeLeftInMillis = 0;
            }

            // อัปเดตแสดงผลเวลาใหม่
            updateTimeDisplay();

            // หยุด Timer ที่กำลังทำงานอยู่
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            // เริ่ม Timer ใหม่
            startTimer();

            // เล่นเสียงเมื่อคำตอบผิด
            soundPool.play(incorrectSoundId, 1f, 1f, 0, 0, 1f);

            // สั่นเมื่อคำตอบผิด
            if (vibrator != null) {
                vibrator.vibrate(200); // สั่น 200 มิลลิวินาที
            }
        }

        // ใช้ LayerDrawable เพื่อวางไอคอนทับบนรูปภาพ
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{imageDrawable, icon});

        // กำหนดขนาดไอคอนและรูปภาพ
        layerDrawable.setLayerInset(1, 0, 0, 0, 0); // กำหนดตำแหน่งไอคอน

        // ตั้งค่าภาพและไอคอนให้กับปุ่ม
        button.setCompoundDrawablesWithIntrinsicBounds(null, layerDrawable, null, null);

        // ปรับขนาด Drawable บนปุ่มให้เป็น 50 x 50 dp เสมอ
        resizeButtonDrawables(new Button[]{button});

        // Check if all correct ingredients are clicked
        if (correctClicks == currentRecipeImages.size()) {
            // Go to next game
            nextGame();
        }
    }


    // เมื่อกดปุ่ม Pause
    private void showCustomPauseDialog() {
        // ตรวจสอบก่อนว่า Dialog กำลังแสดงอยู่หรือไม่
        if (isPauseDialogShowing) {
            return;  // ถ้า Dialog แสดงอยู่แล้ว ไม่ให้เปิดซ้ำ
        }

        // เล่นเสียงเมื่อกดปุ่ม Pause
        soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);

        // เช็คว่า backing sound เล่นอยู่ไม่ ให้หยุดเมื่อกดปุ่ม pause
        if (backingSoundPlayer.isPlaying()) {
            backingSoundPlayer.pause(); // หยุดเสียงชั่วคราว
        }

        // หยุด Timer เมื่อกด Pause
        if (countDownTimer != null) {
            countDownTimer.cancel();  // หยุด Timer
        }

        // สร้าง Dialog และกำหนด Layout ที่เราสร้างขึ้น
        Dialog pauseDialog = new Dialog(this);
        pauseDialog.setContentView(R.layout.dialog_pause);  // เชื่อมโยงกับ layout ที่สร้าง
        pauseDialog.setCancelable(false); // ไม่อนุญาตให้กดปุ่ม Back เพื่อปิด Dialog
        pauseDialog.setCanceledOnTouchOutside(false); // ไม่อนุญาตให้แตะพื้นที่อื่นเพื่อปิด Dialog

        Objects.requireNonNull(pauseDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // กำหนดฟังก์ชันให้กับปุ่มใน Dialog
        Button btnContinue = pauseDialog.findViewById(R.id.btnContinue);
        Button btnNewGame = pauseDialog.findViewById(R.id.btnNewGame);
        Button btnHome = pauseDialog.findViewById(R.id.btnHome);

        // ปุ่ม Continue: ปิด Dialog และดำเนินการต่อในเกม
        btnContinue.setOnClickListener(v -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);

            // กดปุ่ม Continue แล้วเล่น backing track ต่อ
            if (!backingSoundPlayer.isPlaying()) {
                backingSoundPlayer.start(); // เล่นเสียงต่อ
            }

            pauseDialog.dismiss();  // ปิด Dialog
            startTimer();  // เริ่ม Timer ต่อ
        });

        // ปุ่ม New Game: เริ่มเกมใหม่
        btnNewGame.setOnClickListener(v -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);

            // เริ่มเล่น backing tack ใหม่ตั้งแต่ต้น
            if (backingSoundPlayer != null) {
                backingSoundPlayer.seekTo(0); // เลื่อนตำแหน่งการเล่นกลับไปที่จุดเริ่มต้น
                backingSoundPlayer.start();  // เริ่มเล่นเพลงใหม่
            }

            pauseDialog.dismiss();  // ปิด Dialog
            restartGame();  // เริ่มเกมใหม่
        });

        // ปุ่ม Home: กลับไปหน้า Home
        btnHome.setOnClickListener(v -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);

            // หยุดเสียงพื้นหลังและปล่อยทรัพยากร
            if (backingSoundPlayer != null) {
                backingSoundPlayer.stop(); // หยุดการเล่น
                backingSoundPlayer.release(); // ปล่อยทรัพยากร
                backingSoundPlayer = null; // ตั้งค่าเป็น null
            }

            pauseDialog.dismiss();  // ปิด Dialog
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);  // ไปที่หน้า Home
            finish();  // ปิดหน้าปัจจุบัน
        });

        // แสดง Dialog
        pauseDialog.show();

        // อัพเดตสถานะเมื่อ Dialog แสดง
        isPauseDialogShowing = true;

        // กำหนดให้ปิด Dialog เมื่อ Dialog ถูกปิด
        pauseDialog.setOnDismissListener(dialog -> {
            isPauseDialogShowing = false;  // เมื่อ Dialog ถูกปิด, อัพเดตสถานะ
        });
    }

    // ฟังก์ชันสำหรับรีเซ็ตเกม
    private void restartGame() {
        // รีเซ็ตตัวแปรทั้งหมดที่เกี่ยวข้องกับเกม
        gameCount = 0;
        score = 0;
        correctClicks = 0;
        timeLeftInMillis = START_TIME_IN_MILLIS;  // รีเซ็ตเวลาเริ่มต้น

        // เริ่มเกมใหม่
        nextGame();  // เริ่มเกมใหม่

        // เริ่มจับเวลาใหม่
        startTimer();
    }

    // ฟังก์ชันเริ่มจับเวลา
    private void startTimer() {

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimeDisplay();  // อัปเดต UI
            }

            @Override
            public void onFinish() {



                // เมื่อเวลาหมด
                Intent intent = new Intent(GameActivity.this, TimesUpActivity.class);
                intent.putExtra("SCORE", score);  // ส่งคะแนนไปยังหน้า TimesUpActivity
                startActivity(intent);
                finish();

                // หยุดเสียงพื้นหลังและปล่อยทรัพยากร
                if (backingSoundPlayer != null) {
                    backingSoundPlayer.stop(); // หยุดการเล่น
                }

                // เล่นเสียงเมื่อเวลาหมด
                soundPool.play(timesUpSoundId, 1f, 1f, 0, 0, 1f);
            }
        }.start();  // เริ่ม Timer
    }


    private void updateTimeDisplay() {
        // แปลงเวลาที่เหลือเป็นนาทีและวินาที
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        int minutes = (int) ((timeLeftInMillis / 1000) / 60);

        // อัปเดตข้อความแสดงเวลาใน TextView
        timerTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));

        // อัปเดต ProgressBar ตามเวลาที่เหลือ
        int progress = (int) ((timeLeftInMillis * 100) / START_TIME_IN_MILLIS);  // คำนวณโปรเกรสจากเวลาที่เหลือ
        progressBar.setProgress(progress);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // เช็คว่าเกมอยู่ในสถานะ pause หรือไม่
        if (isPaused) {
            showCustomPauseDialog();  // แสดง Dialog Pause ถ้าเกมอยู่ในสถานะ Pause
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // หยุด Timer เมื่อแอปถูกย่อหรือไปหน้าจออื่น
        if (countDownTimer != null) {
            countDownTimer.cancel();  // หยุดการจับเวลา
        }

        if (backingSoundPlayer.isPlaying()) {
            backingSoundPlayer.pause(); // หยุดเสียงชั่วคราว
        }

        // ตั้งค่า isPaused เป็น true เมื่อแอปถูกหยุด
        isPaused = true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        if (backingSoundPlayer != null) {
            backingSoundPlayer.release();
            backingSoundPlayer = null;
        }
    }
}