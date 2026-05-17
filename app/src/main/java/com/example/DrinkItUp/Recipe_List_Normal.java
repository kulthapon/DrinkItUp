package com.example.DrinkItUp;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class Recipe_List_Normal {

    public static List<Recipe> getNormalRecipes(Context context) {
        List<Recipe> recipeList = new ArrayList<>();

        List<Integer> normal01 = new ArrayList<>();
        normal01.add(R.drawable.apple);
        normal01.add(R.drawable.cocktail);
        normal01.add(R.drawable.lemon);
        String recipeName01 = context.getString(R.string.apple_punch);
        String recipeHint01 = context.getString(R.string.apple_punch_hint);
        recipeList.add(new Recipe(recipeName01, recipeHint01 , normal01));

        List<Integer> normal02 = new ArrayList<>();
        normal02.add(R.drawable.strawberry);
        normal02.add(R.drawable.blue_berries);
        normal02.add(R.drawable.juices);
        String recipeName02 = context.getString(R.string.berry_cooler);
        String recipeHint02 = context.getString(R.string.berry_cooler_hint);
        recipeList.add(new Recipe(recipeName02, recipeHint02 , normal02));

        List<Integer> normal03 = new ArrayList<>();
        normal03.add(R.drawable.strawberry);
        normal03.add(R.drawable.lemon);
        normal03.add(R.drawable.juices);
        String recipeName03 = context.getString(R.string.berry_lemonade);
        String recipeHint03 = context.getString(R.string.berry_lemonade_hint);
        recipeList.add(new Recipe(recipeName03, recipeHint03 , normal03));

        List<Integer> normal04 = new ArrayList<>();
        normal04.add(R.drawable.coconut);
        normal04.add(R.drawable.strawberry);
        normal04.add(R.drawable.juices);
        String recipeName04 = context.getString(R.string.coco_berry);
        String recipeHint04 = context.getString(R.string.coco_berry_hint);
        recipeList.add(new Recipe(recipeName04, recipeHint04 , normal04));

        List<Integer> normal05 = new ArrayList<>();
        normal05.add(R.drawable.chocolate);
        normal05.add(R.drawable.milk);
        normal05.add(R.drawable.ice);
        String recipeName05 = context.getString(R.string.choco_freeze);
        String recipeHint05 = context.getString(R.string.choco_freeze_hint);
        recipeList.add(new Recipe(recipeName05, recipeHint05 , normal05));

        List<Integer> normal06 = new ArrayList<>();
        normal06.add(R.drawable.chocolate);
        normal06.add(R.drawable.mint);
        normal06.add(R.drawable.green_tea);
        String recipeName06 = context.getString(R.string.choco_mint_tea);
        String recipeHint06 = context.getString(R.string.choco_mint_tea_hint);
        recipeList.add(new Recipe(recipeName06, recipeHint06 , normal06));

        List<Integer> normal07 = new ArrayList<>();
        normal07.add(R.drawable.apple);
        normal07.add(R.drawable.cocktail);
        normal07.add(R.drawable.ice);
        String recipeName07 = context.getString(R.string.cider_fizz);
        String recipeHint07 = context.getString(R.string.cider_fizz_hint);
        recipeList.add(new Recipe(recipeName07, recipeHint07 , normal07));

        List<Integer> normal08 = new ArrayList<>();
        normal08.add(R.drawable.coconut);
        normal08.add(R.drawable.cocktail);
        normal08.add(R.drawable.ice);
        String recipeName08 = context.getString(R.string.coconut_delight);
        String recipeHint08 = context.getString(R.string.coconut_delight_hint);
        recipeList.add(new Recipe(recipeName08, recipeHint08 , normal08));

        List<Integer> normal09 = new ArrayList<>();
        normal09.add(R.drawable.grapes);
        normal09.add(R.drawable.cocktail);
        normal09.add(R.drawable.ice);
        String recipeName09 = context.getString(R.string.frozen_grape);
        String recipeHint09 = context.getString(R.string.frozen_grape_hint);
        recipeList.add(new Recipe(recipeName09, recipeHint09 , normal09));

        List<Integer> normal10 = new ArrayList<>();
        normal10.add(R.drawable.green_tea);
        normal10.add(R.drawable.honey);
        normal10.add(R.drawable.lemon);
        String recipeName10 = context.getString(R.string.golden_tea);
        String recipeHint10 = context.getString(R.string.golden_tea_hint);
        recipeList.add(new Recipe(recipeName10, recipeHint10 , normal10));

        List<Integer> normal11 = new ArrayList<>();
        normal11.add(R.drawable.honey);
        normal11.add(R.drawable.lemon);
        normal11.add(R.drawable.juices);
        String recipeName11 = context.getString(R.string.honey_lemonade);
        String recipeHint11 = context.getString(R.string.honey_lemonade_hint);
        recipeList.add(new Recipe(recipeName11, recipeHint11 , normal11));

        List<Integer> normal12 = new ArrayList<>();
        normal12.add(R.drawable.coffee);
        normal12.add(R.drawable.chocolate);
        normal12.add(R.drawable.milk);
        String recipeName12 = context.getString(R.string.hot_mocha);
        String recipeHint12 = context.getString(R.string.hot_mocha_hint);
        recipeList.add(new Recipe(recipeName12, recipeHint12 , normal12));

        List<Integer> normal13 = new ArrayList<>();
        normal13.add(R.drawable.mango);
        normal13.add(R.drawable.ice_cream);
        normal13.add(R.drawable.milk);
        String recipeName13 = context.getString(R.string.mango_cream);
        String recipeHint13 = context.getString(R.string.mango_cream_hint);
        recipeList.add(new Recipe(recipeName13, recipeHint13 , normal13));

        List<Integer> normal14 = new ArrayList<>();
        normal14.add(R.drawable.coffee);
        normal14.add(R.drawable.mint);
        normal14.add(R.drawable.chocolate);
        String recipeName14 = context.getString(R.string.mint_choco);
        String recipeHint14 = context.getString(R.string.mint_choco_hint);
        recipeList.add(new Recipe(recipeName14, recipeHint14 , normal14));

        List<Integer> normal15 = new ArrayList<>();
        normal15.add(R.drawable.mint);
        normal15.add(R.drawable.cocktail);
        normal15.add(R.drawable.ice);
        String recipeName15 = context.getString(R.string.mint_mojito);
        String recipeHint15 = context.getString(R.string.mint_mojito_hint);
        recipeList.add(new Recipe(recipeName15, recipeHint15 , normal15));

        List<Integer> normal16 = new ArrayList<>();
        normal16.add(R.drawable.orange);
        normal16.add(R.drawable.honey);
        normal16.add(R.drawable.juices);
        String recipeName16 = context.getString(R.string.orange_bliss);
        String recipeHint16 = context.getString(R.string.orange_bliss_hint);
        recipeList.add(new Recipe(recipeName16, recipeHint16 , normal16));

        List<Integer> normal17 = new ArrayList<>();
        normal17.add(R.drawable.peach);
        normal17.add(R.drawable.cocktail);
        normal17.add(R.drawable.ice);
        String recipeName17 = context.getString(R.string.peach_soda);
        String recipeHint17 = context.getString(R.string.peach_soda_hint);
        recipeList.add(new Recipe(recipeName17, recipeHint17 , normal17));

        List<Integer> normal18 = new ArrayList<>();
        normal18.add(R.drawable.pineapple);
        normal18.add(R.drawable.lemon);
        normal18.add(R.drawable.ice);
        String recipeName18 = context.getString(R.string.pineapple_crush);
        String recipeHint18 = context.getString(R.string.pineapple_crush_hint);
        recipeList.add(new Recipe(recipeName18, recipeHint18 , normal18));

        List<Integer> normal19 = new ArrayList<>();
        normal19.add(R.drawable.strawberry);
        normal19.add(R.drawable.cocktail);
        normal19.add(R.drawable.milk);
        String recipeName19 = context.getString(R.string.pink_bliss);
        String recipeHint19 = context.getString(R.string.pink_bliss_hint);
        recipeList.add(new Recipe(recipeName19, recipeHint19 , normal19));

        List<Integer> normal20 = new ArrayList<>();
        normal20.add(R.drawable.strawberry);
        normal20.add(R.drawable.ice_cream);
        normal20.add(R.drawable.milk);
        String recipeName20 = context.getString(R.string.strawberry_cream);
        String recipeHint20 = context.getString(R.string.strawberry_cream_hint);
        recipeList.add(new Recipe(recipeName20, recipeHint20 , normal20));

        List<Integer> normal21 = new ArrayList<>();
        normal21.add(R.drawable.pineapple);
        normal21.add(R.drawable.strawberry);
        normal21.add(R.drawable.cocktail);
        String recipeName21 = context.getString(R.string.summer_breeze);
        String recipeHint21 = context.getString(R.string.summer_breeze_hint);
        recipeList.add(new Recipe(recipeName21, recipeHint21 , normal21));

        List<Integer> normal22 = new ArrayList<>();
        normal22.add(R.drawable.chocolate);
        normal22.add(R.drawable.milk);
        normal22.add(R.drawable.honey);
        String recipeName22 = context.getString(R.string.warm_choco);
        String recipeHint22 = context.getString(R.string.warm_choco_hint);
        recipeList.add(new Recipe(recipeName22, recipeHint22 , normal22));

        List<Integer> normal23 = new ArrayList<>();
        normal23.add(R.drawable.orange);
        normal23.add(R.drawable.lemon);
        normal23.add(R.drawable.honey);
        String recipeName23 = context.getString(R.string.zesty_orange);
        String recipeHint23 = context.getString(R.string.zesty_orange_hint);
        recipeList.add(new Recipe(recipeName23, recipeHint23 , normal23));

        return recipeList;
    }
}
