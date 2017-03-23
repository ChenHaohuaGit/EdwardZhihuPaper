package com.example.core.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CommentResponse {

@SerializedName("comments")
@Expose
private List<Comment> comments = new ArrayList<>();

/**
* 
* @return
* The comments
*/
public List<Comment> getComments() {
return comments;
}

    @SerializedName("")
    public String comment;


/**
* 
* @param comments
* The comments
*/
public void setComments(List<Comment> comments) {
this.comments = comments;
}

}