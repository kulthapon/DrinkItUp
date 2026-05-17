package com.example.DrinkItUp;

import android.text.SpannableString;

import java.util.List;

public class Recipe {
    private final String name;
    private String description;
    private final List<Integer> imageIds;

    public Recipe(String name, String description, List<Integer> imageIds) {
        this.name = name;
        this.description = description;
        this.imageIds = imageIds;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getImageIds() {
        return imageIds;
    }

    // เพิ่ม setDescription() ที่รับ SpannableString
    public void setDescription(SpannableString description) {
        this.description = description.toString(); // แปลงกลับเป็น String ถ้าต้องการเก็บในรูปแบบ String
    }
}