package com.example.DrinkItUp;

import android.content.Intent;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

public class TimesUpActivity extends AppCompatActivity {

    private TextView highScoreTextView; // ประกาศตัวแปรสำหรับ TextView ที่ใช้แสดงคะแนน
    private DatabaseHelper dbHelper; // ตัวแปรสำหรับการเชื่อมต่อฐานข้อมูล

    // ตัวแปรสำหรับ SoundPool
    private SoundPool soundPool;
    private int clickSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_up); // กำหนด Layout ของ Activity นี้

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

        // ผูก TextView กับตัวแปรในโค้ด
        TextView finalScoreTextView = findViewById(R.id.finalScoreTextView);
        highScoreTextView = findViewById(R.id.highScoreTextView);

        // รับคะแนนจาก Intent
        // ตัวแปรสำหรับเก็บคะแนนสุดท้าย
        int finalScore = getIntent().getIntExtra("SCORE", 0);

        // แปลงคะแนนเป็น String และแสดงใน TextView
        finalScoreTextView.setText(String.valueOf(finalScore));

        // สร้าง DatabaseHelper เพื่อเชื่อมต่อฐานข้อมูล
        dbHelper = new DatabaseHelper(this);

        // เรียกฟังก์ชันเพื่ออัปเดตคะแนนสูงสุด
        updateHighScore(finalScore);

        // กำหนดการทำงานของปุ่ม Try Again
        Button btnTryAgain = findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(v -> {
            // เล่นเสียงคลิกเมื่อกดปุ่ม
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);

            // เปิด GameActivity เพื่อเริ่มเกมใหม่
            Intent intent = new Intent(TimesUpActivity.this, GameActivity.class);
            startActivity(intent);
            finish(); // ปิด Activity ปัจจุบัน
        });

        // กำหนดการทำงานของปุ่ม Home
        Button btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(v -> {
            // เล่นเสียงคลิกเมื่อกดปุ่ม
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);

            // เปิด MainActivity เพื่อกลับไปหน้าหลัก
            Intent intent = new Intent(TimesUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // ปิด Activity ปัจจุบัน
        });

        // หา LinearLayout
        // Layout หลัก
        LinearLayout timesUp = findViewById(R.id.TimesUp);

        // โหลดสถานะ Dark Mode จาก SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        
        // ตั้งค่าพื้นหลังตามโหมดที่เลือก
        if (isDarkMode) {
            timesUp.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black)); // สีดำ
        } else {
            timesUp.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white)); // สีขาว
        }
    }

    /**
     * ฟังก์ชันสำหรับอัปเดตคะแนนสูงสุด
     *
     * @param score คะแนนสุดท้ายของผู้เล่น
     */
    private void updateHighScore(int score) {
        int highScore = getHighScore();
        // ปิด Cursor หลังใช้งาน

        // เปรียบเทียบคะแนนปัจจุบันกับคะแนนสูงสุด
        if (score > highScore) {
            // ถ้าคะแนนใหม่สูงกว่า อัปเดตฐานข้อมูลและ TextView
            dbHelper.addRecord(score);
            highScoreTextView.setText(String.valueOf(score));
        } else {
            // ถ้าคะแนนใหม่ไม่สูงกว่า ให้แสดงคะแนนเดิม
            highScoreTextView.setText(String.valueOf(highScore));
        }
    }

    private int getHighScore() {
        int highScore = 0; // ตัวแปรสำหรับเก็บคะแนนสูงสุด
        // ตัวแปร Cursor สำหรับดึงข้อมูลจากฐานข้อมูล

        try (Cursor cursor = dbHelper.getHighestScore()) {
            // ดึงคะแนนสูงสุดจากฐานข้อมูล
            if (cursor != null && cursor.moveToFirst()) { // ตรวจสอบว่ามีข้อมูลใน Cursor
                int columnIndex = cursor.getColumnIndex(DatabaseHelper.COL_SCORE);
                if (columnIndex != -1) { // ตรวจสอบว่า Column มีอยู่จริง
                    highScore = cursor.getInt(columnIndex);
                }
            }
        }
        return highScore;
    }
}
