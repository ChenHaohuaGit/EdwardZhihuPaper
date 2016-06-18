package com.example.edwardlucci.edwardzhihupaper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.orhanobut.logger.Logger;

/**
 * Created by edward on 16/6/18.
 */
public class StoryDatabaseHelper extends SQLiteOpenHelper{


    public static final String DB_NAME = "zhihupaper.db";
    public static final int DB_VERSION = 1;

    public StoryDatabaseHelper(Context context){
        super(context,DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logger.i(StoryDatabaseContract.SQL_CREATE_ENTRIES);
        db.execSQL(StoryDatabaseContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
