package com.example.DrinkItUp;

import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.res.TypedArray;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.SwitchCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private LinearLayout mainLayout;


    // ตัวแปรสำหรับ SoundPool
    private SoundPool soundPool;
    private int clickSoundId;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // สร้าง SoundPool และกำหนด AudioAttributes
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10) // กำหนดจำนวนเสียงที่สามารถเล่นพร้อมกัน
                .setAudioAttributes(audioAttributes)
                .build();

        // โหลดเสียงต่าง ๆ
        clickSoundId = soundPool.load(this, R.raw.click_sound, 1);

        // ค้นหาองค์ประกอบใน layout
        Button btnGame = findViewById(R.id.btnGame);
        Button btnRecipe = findViewById(R.id.btnRecipe);
        TextView scoreTextView = findViewById(R.id.highScoreTextView);
        mainLayout = findViewById(R.id.mainLayout);
        SwitchCompat toggleSwitch = findViewById(R.id.toggleSwitch);

        // ตั้งค่าการคลิกปุ่มไปที่หน้าเกม
        btnGame.setOnClickListener(v -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        // ตั้งค่าการคลิกปุ่มไปที่หน้า Recipe
        btnRecipe.setOnClickListener(v -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);
            Intent intent = new Intent(MainActivity.this, RecipeActivity_Normal.class);
            startActivity(intent);
        });

        // เชื่อมต่อกับ DatabaseHelper และดึงคะแนนสูงสุด
        dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getHighestScore();
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DatabaseHelper.COL_SCORE);
            if (columnIndex != -1) {
                int highestScore = cursor.getInt(columnIndex);
                scoreTextView.setText(String.valueOf(highestScore));
            }
            cursor.close();
        } else {
            scoreTextView.setText(getString(R.string.no_scores_yet));
        }

        // โหลดสถานะ Dark Mode จาก SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        toggleSwitch.setChecked(isDarkMode); // ตั้งค่าเริ่มต้นให้ Switch ตรงกับสถานะ

        // ตั้งค่าโหมดธีมตามสถานะที่บันทึกไว้
        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        updateBackgroundFromTheme(); // ปรับสีพื้นหลังตามธีม

        // ตั้งค่าการคลิก Toggle Switch
        toggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            updateBackgroundFromTheme(); // อัปเดตสีพื้นหลัง

            // บันทึกสถานะลง SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("darkMode", isChecked);
            editor.apply();
        });
        // Handle back button press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.warning), Toast.LENGTH_SHORT).show();
                    backPressedTime = System.currentTimeMillis();
                }
            }
        });
    }

    private void updateBackgroundFromTheme() {
        // ใช้ R.attr.colorPrimary เพื่อดึงสีหลักจากธีม
        int[] attrs = new int[]{android.R.attr.colorPrimary}; // ใช้ android.R.attr.colorPrimary
        TypedArray typedArray = obtainStyledAttributes(attrs);
        int primaryColor = typedArray.getColor(0, 0x6200EE); // ค่าพื้นฐานถ้าหากไม่ได้กำหนด
        typedArray.recycle();

        // ตั้งค่าพื้นหลังให้กับ Layout
        mainLayout.setBackgroundColor(primaryColor);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close(); // ปิดการเชื่อมต่อฐานข้อมูลเมื่อ Activity ถูกทำลาย
    }

}
