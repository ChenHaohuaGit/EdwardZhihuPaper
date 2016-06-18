//package com.example.edwardlucci.edwardzhihupaper.database;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.edwardlucci.edwardzhihupaper.base.MyApp;
//import com.orhanobut.logger.Logger;
//
///**
// * Created by edward on 16/6/18.
// */
//public class StoryDbHelper extends StoryDatabaseHelper{
//    private static StoryDbHelper ourInstance = new StoryDbHelper(MyApp.get);
//
//    public static StoryDbHelper getInstance() {
//        return ourInstance;
//    }
//
//    public static final String DB_NAME = "zhihupaper.db";
//
//    public static final int DB_VERSION = 1;
//
//    private StoryDbHelper(Context context) {
//        super(context,DB_NAME,null, DB_VERSION);
//    }
//
//    public StoryDatabaseHelper(Context context){
//        super(context,DB_NAME,null, DB_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(StoryDatabaseContract.SQL_CREATE_ENTRIES);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//}
