package com.unist.db.geteat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class DBManager_friend extends SQLiteOpenHelper {
    public DBManager_friend(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FRIEND( User_ID VARCHAR(60) PRIMARY KEY, Name VARCHAR(60),  Bank TEXT, Account TEXT, Debt TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }
    public void insertAFriend(ArrayList<String> FriendDB) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO FRIEND VALUES('"+ FriendDB.get(0) + "', '" + FriendDB.get(1) + "', '" + FriendDB.get(2) + "', '" + FriendDB.get(3) + "', '" + FriendDB.get(4) + "');");
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
    public ArrayList<String> returnNames() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> mlist = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select Name from FRIEND", null);
        while(cursor.moveToNext()) {
            mlist.add(cursor.getString(0));
        }

        return mlist;
    }
    public ArrayList<String> returnDebts() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> mlist = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select Debt from FRIEND", null);
        while(cursor.moveToNext()) {
            mlist.add(cursor.getString(0));
        }

        return mlist;
    }
    public ArrayList<String> returnBank() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> mlist = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select Bank from FRIEND", null);
        while(cursor.moveToNext()) {
            mlist.add(cursor.getString(0));
        }

        return mlist;
    }
    public ArrayList<String> returnAccount() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> mlist = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select Account from FRIEND", null);
        while(cursor.moveToNext()) {
            mlist.add(cursor.getString(0));
        }

        return mlist;
    }
}
