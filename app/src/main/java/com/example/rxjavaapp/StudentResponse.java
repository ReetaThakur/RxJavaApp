package com.example.rxjavaapp;

import java.util.List;

public class StudentResponse {
    private int id;
    private List<Student> studentList;

    public StudentResponse(int id, List<Student> studentList) {
        this.id = id;
        this.studentList = studentList;
    }

    public int getId() {
        return id;
    }

    public List<Student> getStudentList() {
        return studentList;
    }
}
