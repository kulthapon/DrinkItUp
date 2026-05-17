package com.example.DrinkItUp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // ชื่อและเวอร์ชันของฐานข้อมูล
    public static final String DATABASE_NAME = "HighScore.db";
    public static final int DATABASE_VERSION = 1;

    // ชื่อและคอลัมน์ของตาราง
    public static final String TABLE_NAME = "score_records";
    public static final String COL_ID = "id";
    public static final String COL_SCORE = "score";

    // คอนสตรัคเตอร์
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // เมธอดสำหรับสร้างตารางในฐานข้อมูล
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SCORE + " REAL);";  // กำหนดคอลัมน์ id และ score
        db.execSQL(createTable);  // สร้างตาราง
    }

    // เมธอดสำหรับอัปเกรดฐานข้อมูล (ใช้เมื่อมีการเปลี่ยนแปลงเวอร์ชัน)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // ลบตารางเก่าแล้วสร้างตารางใหม่
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // เมธอดสำหรับเพิ่มคะแนนลงในฐานข้อมูล
    public void addRecord(int score) {
        SQLiteDatabase db = this.getWritableDatabase();  // เปิดฐานข้อมูลในโหมดเขียน
        ContentValues values = new ContentValues();
        values.put(COL_SCORE, score);  // เพิ่มค่าคะแนนลงใน ContentValues
        db.insert(TABLE_NAME, null, values);  // แทรกข้อมูลลงในตาราง
    }

    // ดึงข้อมูลคะแนนสูงสุด
    public Cursor getHighestScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        // ตรวจสอบว่าใช้ชื่อคอลัมน์ถูกต้องใน SQL Query
        return db.rawQuery("SELECT id AS _id, score FROM " + TABLE_NAME + " ORDER BY " + COL_SCORE + " DESC LIMIT 1", null);
    }
}
