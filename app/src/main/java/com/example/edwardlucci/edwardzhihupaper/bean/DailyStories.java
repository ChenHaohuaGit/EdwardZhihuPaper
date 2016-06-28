package com.example.edwardlucci.edwardzhihupaper.bean;

import android.content.ContentValues;

import com.example.edwardlucci.edwardzhihupaper.database.DateDatabaseContract;
import com.example.edwardlucci.edwardzhihupaper.database.StoryDatabaseContract;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DailyStories {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("stories")
    @Expose
    private List<Story> stories = new ArrayList<>();

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
    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public ContentValues dailyStoriesDate2ContentValues(String currentDate){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateDatabaseContract.DateTable.COLUMN_NAME_DATE,currentDate);
        contentValues.put(DateDatabaseContract.DateTable.COLUMN_NAME_PREVIOUS_DATE,this.date);
        return contentValues;
    }

    @Override
    public String toString() {
        return "DailyStories{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                '}';
    }
}
