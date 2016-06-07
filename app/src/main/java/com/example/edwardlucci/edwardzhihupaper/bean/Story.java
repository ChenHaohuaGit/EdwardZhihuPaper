package com.example.edwardlucci.edwardzhihupaper.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Story implements Serializable{

    @SerializedName("images")
    @Expose
    private List<String> images = new ArrayList<String>();
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ga_prefix")
    @Expose
    private String gaPrefix;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("multipic")
    @Expose
    private Boolean multipic;

    /**
     * @return The images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * @return The type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(Integer type) {
        this.type = type;
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
     * @return The gaPrefix
     */
    public String getGaPrefix() {
        return gaPrefix;
    }

    /**
     * @param gaPrefix The ga_prefix
     */
    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
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

    /**
     * @return The multipic
     */
    public Boolean getMultipic() {
        return multipic;
    }

    /**
     * @param multipic The multipic
     */
    public void setMultipic(Boolean multipic) {
        this.multipic = multipic;
    }

}
