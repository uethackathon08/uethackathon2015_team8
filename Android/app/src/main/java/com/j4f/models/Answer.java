package com.j4f.models;

import java.util.Date;

/**
 * Created by hunte on 11/21/2015.
 */
public class Answer {
    private String userId;
    private String nameOfUser;
    private String userAvatarLink;
    private String content;
    private String imageLink;
    private int voteUp;
    private int voteDown;
    private Date timestamp;

    public Answer(String userId, String nameOfUser, String userAvatarLink, String content, String imageLink, int voteUp, int voteDown, Date timestamp) {
        this.userId = userId;
        this.nameOfUser = nameOfUser;
        this.userAvatarLink = userAvatarLink;
        this.content = content;
        this.imageLink = imageLink;
        this.voteUp = voteUp;
        this.voteDown = voteDown;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getUserAvatarLink() {
        return userAvatarLink;
    }

    public void setUserAvatarLink(String userAvatarLink) {
        this.userAvatarLink = userAvatarLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getVoteUp() {
        return voteUp;
    }

    public void setVoteUp(int voteUp) {
        this.voteUp = voteUp;
    }

    public int getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(int voteDown) {
        this.voteDown = voteDown;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
