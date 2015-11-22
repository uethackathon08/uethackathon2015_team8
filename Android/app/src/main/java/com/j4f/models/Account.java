package com.j4f.models;

/**
 * Created by hunter on 11/21/2015.
 */
public class Account {
    private String username;
    private String password;
    private String name;
    private String email;
    private String mobile;
    private int reputation;
    private int credit;

    public Account(String username, String password, String name, String email, String mobile, int reputation, int credit) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.reputation = reputation;
        this.credit = credit;
    }

    public Account(String username, int reputation) {
        this.username = username;
        this.reputation = reputation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
