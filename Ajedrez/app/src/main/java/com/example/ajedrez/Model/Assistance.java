package com.example.ajedrez.Model;

import java.util.List;

public class Assistance {
    public Boolean assisted;
    Student student;
    List<Subject> subjects;

    public Assistance(Student student, List<Subject> subjects) {
        this.student = student;
        this.subjects = subjects;
    }

    public Assistance(Student student) {
        this.student = student;
    }

    public void setAssisted(boolean assisted) {
        this.assisted = assisted;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isAssisted() {
        return assisted;
    }
}
