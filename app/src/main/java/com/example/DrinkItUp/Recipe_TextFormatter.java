package com.example.DrinkItUp;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import androidx.core.content.ContextCompat;

public class Recipe_TextFormatter {

    // ฟังก์ชันที่ใช้เน้นคำบางคำในคำอธิบาย
    public static SpannableString getFormattedDescription(Context context, String description) {
        SpannableString spannableDescription = new SpannableString(description);

        // คำที่ต้องการเปลี่ยนสี
        String[] wordsToHighlight = {
                "blend", "Bold", "bold", "bubbly", "burst",
                "chill", "chilly", "chocolate", "chocolatey", "Citrusy", "colorful", "cool", "Creamy", "creamy", "Crisp",
                "Exotic",
                "finish", "fizzy", "flavors", "Fresh", "Fruity", "fruity",
                "goodness",
                "Herbal", "herbal",
                "juicy",
                "kick",
                "Lush",
                "mix",
                "refreshing", "refreshingly", "refreshment", "rich",
                "silky", "Smooth", "spicy", "Sweet", "sweet", "sweetness",
                "Tangy", "tangy", "Tart", "tart", "touch", "Tropical", "tropical", "twist",
                "Warm", "warm",
                "zest", "Zesty", "zesty"
        };

        // ดึงสีจาก colors.xml
        int highlightColor = ContextCompat.getColor(context, R.color.dark_pink); // ใช้สีจาก color.xml

        // เน้นคำเหล่านี้ด้วยสีที่ต้องการ
        for (String word : wordsToHighlight) {
            int start = description.indexOf(word);
            while (start != -1) {
                int end = start + word.length();
                spannableDescription.setSpan(new ForegroundColorSpan(highlightColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                start = description.indexOf(word, end);
            }
        }

        return spannableDescription;
    }
}
