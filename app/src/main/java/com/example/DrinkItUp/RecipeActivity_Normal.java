package com.example.DrinkItUp;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import android.content.SharedPreferences;
import androidx.core.content.ContextCompat;

public class RecipeActivity_Normal extends AppCompatActivity {

    private SoundPool soundPool;
    private int clickSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_normal);

        // สร้าง SoundPool และกำหนด AudioAttributes
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();

        // โหลดเสียง Click
        clickSoundId = soundPool.load(this, R.raw.click_sound, 1);

        // เรียกใช้ RecipeListNormal เพื่อดึงข้อมูลสูตรเครื่องดื่ม
        List<Recipe> recipeList = Recipe_List_Normal.getNormalRecipes(this);
        RecipeAdapter adapter = new RecipeAdapter(this, recipeList, R.drawable.recipe_normal);

        // ตั้งค่า Adapter ให้กับ ListView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // ปุ่ม Back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);
            Intent intent = new Intent(RecipeActivity_Normal.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // ปุ่ม Hard
        Button btnHard = findViewById(R.id.btnHard);
        btnHard.setOnClickListener(v -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);
            Intent intent = new Intent(RecipeActivity_Normal.this, RecipeActivity_Hard.class);
            startActivity(intent);
        });

        // ตั้งค่าพื้นหลังตามโหมดที่เลือก
        LinearLayout btnRecipe = findViewById(R.id.btnRecipe);
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        btnRecipe.setBackgroundColor(ContextCompat.getColor(this,
                isDarkMode ? android.R.color.black : android.R.color.white));

        // ใช้ OnBackPressedDispatcher สำหรับการกดปุ่ม Back
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);
                Intent intent = new Intent(RecipeActivity_Normal.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
