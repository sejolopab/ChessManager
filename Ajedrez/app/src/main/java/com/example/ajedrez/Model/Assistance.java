package com.example.ajedrez.Model;

public class Assistance {
    public Boolean assisted;
    private Student student;

    //DO NOT REMOVE
    public Assistance() {}

    public Assistance(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setAssisted(boolean assisted) {
        this.assisted = assisted;
    }
}
