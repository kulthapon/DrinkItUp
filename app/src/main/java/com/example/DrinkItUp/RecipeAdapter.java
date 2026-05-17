package com.example.DrinkItUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import java.util.List;

public class RecipeAdapter extends BaseAdapter {

    private final Context context;
    private final List<Recipe> recipeList;
    private final int backgroundResource; // เก็บ ID ของไฟล์พื้นหลัง

    // Constructor
    public RecipeAdapter(Context context, List<Recipe> recipeList, int backgroundResource) {
        this.context = context;
        this.recipeList = recipeList;
        this.backgroundResource = backgroundResource; // รับไฟล์พื้นหลังจาก Activity
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.recipe_item, parent, false);
        }

        Recipe recipe = recipeList.get(position);

        // ตั้งค่าชื่อเมนู
        TextView nameTextView = convertView.findViewById(R.id.recipeName);
        nameTextView.setText(recipe.getName());

        // ตั้งค่าพื้นหลังของรายการตาม Resource ที่รับเข้ามา
        convertView.setBackgroundResource(backgroundResource);

        // แสดงผลรูปภาพตามข้อมูล
        ImageView imageView1 = convertView.findViewById(R.id.imageView1);
        ImageView imageView2 = convertView.findViewById(R.id.imageView2);
        ImageView imageView3 = convertView.findViewById(R.id.imageView3);
        ImageView imageView4 = convertView.findViewById(R.id.imageView4);
        ImageView imageView5 = convertView.findViewById(R.id.imageView5);

        List<Integer> imageIds = recipe.getImageIds();
        imageView1.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);

        if (!imageIds.isEmpty()) {
            imageView1.setImageResource(imageIds.get(0));
            imageView1.setVisibility(View.VISIBLE);
        }
        if (imageIds.size() > 1) {
            imageView2.setImageResource(imageIds.get(1));
            imageView2.setVisibility(View.VISIBLE);
        }
        if (imageIds.size() > 2) {
            imageView3.setImageResource(imageIds.get(2));
            imageView3.setVisibility(View.VISIBLE);
        }
        if (imageIds.size() > 3) {
            imageView4.setImageResource(imageIds.get(3));
            imageView4.setVisibility(View.VISIBLE);
        }
        if (imageIds.size() > 4) {
            imageView5.setImageResource(imageIds.get(4));
            imageView5.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
