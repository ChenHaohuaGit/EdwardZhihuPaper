package com.example.edwardlucci.edwardzhihupaper.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by edward on 16/6/18.
 */
public class DatabaseHelper extends SQLiteOpenHelper{


    public static final String DB_NAME = "zhihudaily.db";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StoryDatabaseContract.SQL_CREATE_ENTRIES);
        db.execSQL(DateDatabaseContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
