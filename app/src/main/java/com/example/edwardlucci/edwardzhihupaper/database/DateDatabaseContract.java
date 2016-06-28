package com.example.edwardlucci.edwardzhihupaper.database;

import android.provider.BaseColumns;

public final class DateDatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.

    public DateDatabaseContract() {
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String UNIQUE = " UNIQUE";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DateTable.TABLE_NAME + " (" +
                    DateTable._ID + " INTEGER PRIMARY KEY," +
                    DateTable.COLUMN_NAME_DATE + TEXT_TYPE +UNIQUE+ COMMA_SEP +
                    DateTable.COLUMN_NAME_PREVIOUS_DATE + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DateTable.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static abstract class DateTable implements BaseColumns {
        public static final String TABLE_NAME = "date";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_PREVIOUS_DATE = "previous_date";
    }

    static final public String[] projection = {
            DateTable._ID,
            DateTable.COLUMN_NAME_DATE,
            DateTable.COLUMN_NAME_PREVIOUS_DATE
    };

}
