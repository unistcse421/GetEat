package com.unist.am.lineup;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mintaewon on 2015. 7. 12..
 */
public class DBManager_table extends SQLiteOpenHelper {
    public DBManager_table(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE TABLE_INFO( pid INTEGER PRIMARY KEY, start_time TEXT, num TEXT);");
        try {

            sqLiteDatabase.execSQL("INSERT INTO TABLE_INFO VALUES(1, '0', '0')");
            sqLiteDatabase.execSQL("INSERT INTO TABLE_INFO VALUES(2, '0', '0')");
            sqLiteDatabase.execSQL("INSERT INTO TABLE_INFO VALUES(3, '0', '0')");
            sqLiteDatabase.execSQL("INSERT INTO TABLE_INFO VALUES(4, '0', '0')");
            sqLiteDatabase.execSQL("INSERT INTO TABLE_INFO VALUES(5, '0', '0')");
            sqLiteDatabase.execSQL("INSERT INTO TABLE_INFO VALUES(6, '0', '0')");
            sqLiteDatabase.execSQL("INSERT INTO TABLE_INFO VALUES(7, '0', '0')");
            sqLiteDatabase.execSQL("INSERT INTO TABLE_INFO VALUES(8, '0', '0')");
        }
        catch (Exception e){
            Log.e("TABLE DB", e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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


    public String returnPid() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select pid from RESERV_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }

    public String returnParty() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select party from RESERV_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
    public String returnName() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select res_name from RESERV_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
    public String returnDummyname() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select dummy_name from RESERV_INFO", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
    public ArrayList<String> returnTime() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> mlist = new ArrayList<String>();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select start_time from TABLE_INFO", null);
        while(cursor.moveToNext()) {
            mlist.add(cursor.getString(0));
        }

        return mlist;
    }
    public ArrayList<String> returnNum() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> mlist2 = new ArrayList<String>();

        Cursor cursor = db.rawQuery("select num from TABLE_INFO", null);
        while(cursor.moveToNext()) {
            mlist2.add(cursor.getString(0));
        }

        return mlist2;
    }


}
