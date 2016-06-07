package com.example.edwardlucci.edwardzhihupaper.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by edwardlucci on 16/4/23.
 */
public class SplashResponse {
    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("img")
    @Expose
    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}