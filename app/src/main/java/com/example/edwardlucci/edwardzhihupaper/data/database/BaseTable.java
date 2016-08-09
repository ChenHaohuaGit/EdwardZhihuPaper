package com.example.edwardlucci.edwardzhihupaper.data.database;

import android.provider.BaseColumns;

/**
 * Created by edwardlucci on 16/5/25.
 */
public abstract class BaseTable implements BaseColumns {

    public String tableName;

    public String[] columns;

    abstract String getTableName();

    abstract String[] getColumns();
}
