package com.example.myapp;

public class User {
    private String name;
    private int correctAnswers;

    public User() {

    }

    public User(String name, int correctAnswers) {
        this.name = name;
        this.correctAnswers = correctAnswers;
    }

    public String getName() {
        return name;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }
}

