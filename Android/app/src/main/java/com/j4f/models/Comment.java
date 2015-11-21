package com.j4f.models;

import java.util.Date;

/**
 * Created by hunter on 11/22/2015.
 */
public class Comment {
    private String userName;
    private String avatarLink;
    private Date timestamp;
    private String content;
    private int voteUp;
    private int voteDown;

    public Comment(String userName, String avatarLink, Date timestamp, String content, int voteUp, int voteDown) {
        this.userName = userName;
        this.avatarLink = avatarLink;
        this.timestamp = timestamp;
        this.content = content;
        this.voteUp = voteUp;
        this.voteDown = voteDown;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
