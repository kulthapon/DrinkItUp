package com.example.DrinkItUp;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class Recipe_List_Hard {

    public static List<Recipe> getHardRecipes(Context context) {
        List<Recipe> recipeList = new ArrayList<>();

        List<Integer> hard01 = new ArrayList<>();
        hard01.add(R.drawable.strawberry);
        hard01.add(R.drawable.blue_berries);
        hard01.add(R.drawable.lemon);
        hard01.add(R.drawable.honey);
        hard01.add(R.drawable.ice);
        String recipeName01 = context.getString(R.string.berry_breeze);
        String recipeHint01 = context.getString(R.string.berry_breeze_hint);
        recipeList.add(new Recipe(recipeName01, recipeHint01 , hard01));

        List<Integer> hard02 = new ArrayList<>();
        hard02.add(R.drawable.strawberry);
        hard02.add(R.drawable.blue_berries);
        hard02.add(R.drawable.lemon);
        hard02.add(R.drawable.juices);
        hard02.add(R.drawable.ice);
        String recipeName02 = context.getString(R.string.berry_tango);
        String recipeHint02 = context.getString(R.string.berry_tango_hint);
        recipeList.add(new Recipe(recipeName02, recipeHint02 , hard02));

        List<Integer> hard03 = new ArrayList<>();
        hard03.add(R.drawable.lemon);
        hard03.add(R.drawable.mint);
        hard03.add(R.drawable.cocktail);
        hard03.add(R.drawable.green_tea);
        hard03.add(R.drawable.ice);
        String recipeName03 = context.getString(R.string.citrus_mint);
        String recipeHint03 = context.getString(R.string.citrus_mint_hint);
        recipeList.add(new Recipe(recipeName03, recipeHint03 , hard03));

        List<Integer> hard04 = new ArrayList<>();
        hard04.add(R.drawable.cocktail);
        hard04.add(R.drawable.pineapple);
        hard04.add(R.drawable.coconut);
        hard04.add(R.drawable.strawberry);
        hard04.add(R.drawable.ice);
        String recipeName04 = context.getString(R.string.frozen_paradise);
        String recipeHint04 = context.getString(R.string.frozen_paradise_hint);
        recipeList.add(new Recipe(recipeName04, recipeHint04 , hard04));

        List<Integer> hard05 = new ArrayList<>();
        hard05.add(R.drawable.milk);
        hard05.add(R.drawable.honey);
        hard05.add(R.drawable.lemon);
        hard05.add(R.drawable.green_tea);
        hard05.add(R.drawable.ice_cream);
        String recipeName05 = context.getString(R.string.golden_milk);
        String recipeHint05 = context.getString(R.string.golden_milk_hint);
        recipeList.add(new Recipe(recipeName05, recipeHint05 , hard05));

        List<Integer> hard06 = new ArrayList<>();
        hard06.add(R.drawable.mint);
        hard06.add(R.drawable.cocktail);
        hard06.add(R.drawable.lemon);
        hard06.add(R.drawable.ice);
        hard06.add(R.drawable.pineapple);
        String recipeName06 = context.getString(R.string.minty_punch);
        String recipeHint06 = context.getString(R.string.minty_punch_hint);
        recipeList.add(new Recipe(recipeName06, recipeHint06 , hard06));

        List<Integer> hard07 = new ArrayList<>();
        hard07.add(R.drawable.strawberry);
        hard07.add(R.drawable.lemon);
        hard07.add(R.drawable.pineapple);
        hard07.add(R.drawable.grapes);
        hard07.add(R.drawable.cocktail);
        String recipeName07 = context.getString(R.string.rainbow_bliss);
        String recipeHint07 = context.getString(R.string.rainbow_bliss_hint);
        recipeList.add(new Recipe(recipeName07, recipeHint07 , hard07));

        List<Integer> hard08 = new ArrayList<>();
        hard08.add(R.drawable.chili);
        hard08.add(R.drawable.mango);
        hard08.add(R.drawable.lemon);
        hard08.add(R.drawable.cocktail);
        hard08.add(R.drawable.ice);
        String recipeName08 = context.getString(R.string.spicy_mango);
        String recipeHint08 = context.getString(R.string.spicy_mango_hint);
        recipeList.add(new Recipe(recipeName08, recipeHint08 , hard08));

        List<Integer> hard09 = new ArrayList<>();
        hard09.add(R.drawable.orange);
        hard09.add(R.drawable.pineapple);
        hard09.add(R.drawable.lemon);
        hard09.add(R.drawable.honey);
        hard09.add(R.drawable.ice);
        String recipeName09 = context.getString(R.string.summer_splash);
        String recipeHint09 = context.getString(R.string.summer_splash_hint);
        recipeList.add(new Recipe(recipeName09, recipeHint09 , hard09));

        List<Integer> hard10 = new ArrayList<>();
        hard10.add(R.drawable.pineapple);
        hard10.add(R.drawable.coconut);
        hard10.add(R.drawable.mango);
        hard10.add(R.drawable.cocktail);
        hard10.add(R.drawable.ice);
        String recipeName10 = context.getString(R.string.tropical_delight);
        String recipeHint10 = context.getString(R.string.tropical_delight_hint);
        recipeList.add(new Recipe(recipeName10, recipeHint10 , hard10));

        List<Integer> hard11 = new ArrayList<>();
        hard11.add(R.drawable.pineapple);
        hard11.add(R.drawable.coconut);
        hard11.add(R.drawable.lemon);
        hard11.add(R.drawable.cocktail);
        hard11.add(R.drawable.ice);
        String recipeName11 = context.getString(R.string.tropical_fizz);
        String recipeHint11 = context.getString(R.string.tropical_fizz_hint);
        recipeList.add(new Recipe(recipeName11, recipeHint11 , hard11));

        return recipeList;
    }
}
