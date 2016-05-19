package com.unist.am.lineup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mintaewon on 2015. 10. 30..
 */
public class DBManager_userinfo extends SQLiteOpenHelper {
    public DBManager_userinfo(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER_INFO( username VARCHAR(60) PRIMARY KEY);");


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
    public String returnName() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select username from USER_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
}
