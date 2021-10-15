package com.example.rxjavaapp;

public class Student {
    private String s_name;
    private int s_id;
    private double s_marks;

    public Student(String s_name, int s_id, double s_marks) {
        this.s_name = s_name;
        this.s_id = s_id;
        this.s_marks = s_marks;
    }

    public String getS_name() {
        return s_name;
    }

    public int getS_id() {
        return s_id;
    }

    public double getS_marks() {
        return s_marks;
    }
}
