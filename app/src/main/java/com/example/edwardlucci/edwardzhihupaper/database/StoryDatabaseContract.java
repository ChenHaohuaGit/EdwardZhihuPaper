package com.example.edwardlucci.edwardzhihupaper.database;

import android.provider.BaseColumns;


/**
 *
 */
public final class StoryDatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.

    public StoryDatabaseContract() {
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StoryTable.TABLE_NAME + " (" +
                    StoryTable._ID + " INTEGER PRIMARY KEY," +
                    StoryTable.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    StoryTable.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    StoryTable.COLUMN_NAME_IMAGES + TEXT_TYPE + COMMA_SEP +
                    StoryTable.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
                    StoryTable.COLUMN_NAME_MULTIPIC + TEXT_TYPE + COMMA_SEP +
                    StoryTable.COLUMN_NAME_TYPE + TEXT_TYPE+ " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StoryTable.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static abstract class StoryTable implements BaseColumns {
        public static final String TABLE_NAME = "story";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IMAGES = "images";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_MULTIPIC = "multipic";
        public static final String COLUMN_NAME_TYPE = "type";
    }

    static final public String[] projection = {
            StoryTable._ID,
            StoryTable.COLUMN_NAME_DATE,
            StoryTable.COLUMN_NAME_TITLE,
            StoryTable.COLUMN_NAME_IMAGES,
            StoryTable.COLUMN_NAME_ID,
            StoryTable.COLUMN_NAME_MULTIPIC,
            StoryTable.COLUMN_NAME_TYPE
    };

}
