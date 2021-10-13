package com.example.rxjavaapp;

public class Task {
    private int id;
    private String name;
    private boolean isComplete;

    public Task(int id, String name, boolean isComplete) {
        this.id = id;
        this.name = name;
        this.isComplete = isComplete;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
