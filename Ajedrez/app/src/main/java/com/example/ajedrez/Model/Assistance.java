package com.example.ajedrez.Model;

public class Assistance {
    public Boolean assisted;
    private Student student;

    public Assistance() {

    }

    public Assistance(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public Boolean getAssisted() {
        return assisted;
    }

    public void setAssisted(boolean assisted) {
        this.assisted = assisted;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
