package com.example.edwardlucci.edwardzhihupaper.bean;

import android.content.ContentValues;

import com.example.edwardlucci.edwardzhihupaper.database.DateDatabaseContract;
import com.example.edwardlucci.edwardzhihupaper.database.StoryDatabaseContract;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class DailyStories extends RealmObject {

    public static final class SOURCE_TYPE {
        public static final int MEMORY = 0;
        public static final int DATABASE = 1;
        public static final int NETWORK = 2;
    }

    @Ignore
    private int source;

    @SerializedName("date")
    @Expose
    @PrimaryKey
    private String date;

    @SerializedName("stories")
    @Expose
    private RealmList<Story> stories = new RealmList<>();

    private String realDate;

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The stories
     */
    public List<Story> getStories() {
        return stories;
    }

    /**
     * @param stories The stories
     */
    public void setStories(RealmList<Story> stories) {
        this.stories = stories;
    }

    public String getRealDate() {
        return realDate;
    }

    public void setRealDate(String realDate) {
        this.realDate = realDate;
    }

    public ContentValues dailyStoriesDate2ContentValues(String currentDate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateDatabaseContract.DateTable.COLUMN_NAME_DATE, currentDate);
        contentValues.put(DateDatabaseContract.DateTable.COLUMN_NAME_PREVIOUS_DATE, this.date);
        return contentValues;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }


    @Override
    public String toString() {
        return "DailyStories{" +
                "source=" + source +
                ", date='" + date + '\'' +
                ", stories=" + stories +
                ", realDate='" + realDate + '\'' +
                '}';
    }
}
