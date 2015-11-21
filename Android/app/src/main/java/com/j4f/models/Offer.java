package com.j4f.models;

import java.util.ArrayList;

/**
 * Created by nvg58 on 11/21/15.
 * Project J4F
 */
public class Offer {
    private String id;
    private String title;
    private String[] tags;
    private String content;
    private String[] time;
    private String phone;
    private String[] bid_list;
    private String created_at;

    public Offer(String id, String title, String[] tags, String content, String[] time, String phone, String[] bid_list, String created_at) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.content = content;
        this.time = time;
        this.phone = phone;
        this.bid_list = bid_list;
        this.created_at = created_at;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getBid_list() {
        return bid_list;
    }

    public void setBid_list(String[] bid_list) {
        this.bid_list = bid_list;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
