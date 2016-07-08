package com.example.edwardlucci.edwardzhihupaper.bean;

import android.content.ContentValues;

import com.example.edwardlucci.edwardzhihupaper.database.StoryDatabaseContract;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Story extends RealmObject implements Serializable{

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public ContentValues story2contentvalues(String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(StoryDatabaseContract.StoryTable.COLUMN_NAME_DATE,date);
        contentValues.put(StoryDatabaseContract.StoryTable.COLUMN_NAME_ID,this.id);
        contentValues.put(StoryDatabaseContract.StoryTable.COLUMN_NAME_IMAGES, this.image);
        contentValues.put(StoryDatabaseContract.StoryTable.COLUMN_NAME_TITLE,this.title);
        return contentValues;
    }
}
