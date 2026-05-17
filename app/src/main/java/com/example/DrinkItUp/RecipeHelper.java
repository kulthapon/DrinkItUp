package com.example.DrinkItUp;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecipeHelper {

    // ฟังก์ชันสุ่มสูตรเครื่องดื่ม
    public static Recipe getRandomRecipe(Context context, boolean isHardRecipe) {
        List<Recipe> recipeList = new ArrayList<>();

        if (isHardRecipe) {
            recipeList.addAll(Recipe_List_Hard.getHardRecipes(context));
        } else {
            recipeList.addAll(Recipe_List_Normal.getNormalRecipes(context));
        }

        // Randomly select a recipe
        Random random = new Random();
        int randomIndex = random.nextInt(recipeList.size());
        Recipe selectedRecipe = recipeList.get(randomIndex);

        // ใช้ฟังก์ชันเน้นคำในคำอธิบายจาก Recipe_TextFormatter
        selectedRecipe.setDescription(Recipe_TextFormatter.getFormattedDescription(context, selectedRecipe.getDescription()));

        return selectedRecipe;
    }
}
