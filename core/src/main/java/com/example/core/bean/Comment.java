package com.example.core.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Comment{

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("reply_to")
    @Expose
    private ReplyTo replyTo;

    /**
     * @return The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return The time
     */
    public Integer getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(Integer time) {
        this.time = time;
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
     * @return The likes
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * @param likes The likes
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * @return The replyTo
     */
    public ReplyTo getReplyTo() {
        return replyTo;
    }

    /**
     * @param replyTo The reply_to
     */
    public void setReplyTo(ReplyTo replyTo) {
        this.replyTo = replyTo;
    }

    public static class LikesCompare implements Comparator<Comment>{

        @Override
        public int compare(Comment lhs, Comment rhs) {
            return -lhs.getLikes().compareTo(rhs.getLikes());
        }
    }
}