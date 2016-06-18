package com.example.edwardlucci.edwardzhihupaper.bean;

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
    private List<Story> stories = new ArrayList<Story>();
    @SerializedName("top_stories")
    @Expose
    private List<Story> topStories = new ArrayList<Story>();

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

    /**
     * @return The topStories
     */
    public List<Story> getTopStories() {
        return topStories;
    }

    /**
     * @param topStories The top_stories
     */
    public void setTopStories(List<Story> topStories) {
        this.topStories = topStories;
    }

}
