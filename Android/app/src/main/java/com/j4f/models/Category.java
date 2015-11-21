package com.j4f.models;

/**
 * Created by hunter on 11/21/2015.
 */
public class Category {
    private String id;
    private String name;
    private String iconLink;
    private int numberOfQuestion;
    private int numberOfTutor;

    public Category(String id, String name, String iconLink, int numberOfQuestion, int numberOfTutor) {
        this.id = id;
        this.name = name;
        this.iconLink = iconLink;
        this.numberOfQuestion = numberOfQuestion;
        this.numberOfTutor = numberOfTutor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public int getNumberOfTutor() {
        return numberOfTutor;
    }

    public void setNumberOfTutor(int numberOfTutor) {
        this.numberOfTutor = numberOfTutor;
    }
}
