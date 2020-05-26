package com.example.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteOpener extends SQLiteOpenHelper {

    public SQLiteOpener(Context c ){
        super(c, "events", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = " create table events (eventName text , time text , date text , month text , year text , startTime text , endTime text , repeated text , address text )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
