package com.unist.db.geteat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBManager_myinfo extends SQLiteOpenHelper {
    public DBManager_myinfo(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MY_INFO( User_ID VARCHAR(60) PRIMARY KEY, Name VARCHAR(60), Bank TEXT, Account TEXT, Phone TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }
    public void insertMyInfo(ArrayList<String> MyDB) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MY_INFO VALUES('"+ MyDB.get(0) + "', '" + MyDB.get(1) + "', '" + MyDB.get(2) + "', '" + MyDB.get(3) + "', '" + MyDB.get(4) + "');");
        db.close();
    }
    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }
    public String returnName() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select Name from MY_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
    public String returnUser_ID() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select User_ID from MY_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
    public String returnBank() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select Bank from MY_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
    public String returnAccount() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select Account from MY_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
    public String returnPhone() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select Phone from MY_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
}
