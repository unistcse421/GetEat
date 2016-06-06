package com.unist.db.geteat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mintaewon on 2016. 6. 7..
 */
public class DBManager_history extends SQLiteOpenHelper {
    public DBManager_history(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE HISTORY( History_ID INTEGER PRIMARY KEY AUTOINCREMENT, Rest_Name VARCHAR(60),  price VARCHAR(60), date TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
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
    public ArrayList<HistoryItem> returnHistroies() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HistoryItem> hlist = new ArrayList<HistoryItem>();
        Cursor cursor_1 = db.rawQuery("select History_ID from HISTORY", null);
        Cursor cursor_2 = db.rawQuery("select Rest_Name from HISTORY", null);
        Cursor cursor_3 = db.rawQuery("select price from HISTORY", null);
        Cursor cursor_4 = db.rawQuery("select date from HISTORY", null);
        while(cursor_1.moveToNext() && cursor_2.moveToNext() && cursor_3.moveToNext() && cursor_4.moveToNext()) {
            hlist.add(new HistoryItem(cursor_1.getString(0),cursor_2.getString(0),cursor_3.getString(0),cursor_4.getString(0)));
            Log.e("db: ",cursor_1.getString(0)+cursor_2.getString(0)+cursor_3.getString(0)+cursor_4.getString(0));
        }

        return hlist;
    }

}
