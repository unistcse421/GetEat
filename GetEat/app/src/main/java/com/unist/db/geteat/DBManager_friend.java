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
        db.execSQL("CREATE TABLE FRIEND( User_ID VARCHAR(60) PRIMARY KEY, Name VARCHAR(60),  Bank TEXT, Account TEXT, Debt INTEGER);");


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
        db.execSQL("INSERT INTO FRIEND VALUES('"+ FriendDB.get(0) + "', '" + FriendDB.get(1) + "', '" + FriendDB.get(2) + "', '" + FriendDB.get(3) + "', '" + Integer.parseInt(FriendDB.get(4)) + "');");
        db.close();
    }
    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }
    public void distributeMoney(String debt, String p_name) {
        SQLiteDatabase db = getWritableDatabase();
        Integer Origin = 0;
        Cursor cursor = db.rawQuery("select Debt from FRIEND where Name = '" +p_name +"'", null);
        while(cursor.moveToNext()) {
            Origin = cursor.getInt(0);
        }
        Integer Total = Integer.parseInt(debt) + Origin;
        db.execSQL("update FRIEND set Debt = '" + Total +"' where Name = '"+p_name +"'");
        db.close();
    }
    public void endDebt(String name) {
        SQLiteDatabase db = getWritableDatabase();
        Integer zero = 0;
        db.execSQL("update FRIEND set Debt = '" + zero +"' where Name = '" + name +"'");
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
