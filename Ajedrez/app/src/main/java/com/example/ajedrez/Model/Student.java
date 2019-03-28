package com.example.ajedrez.Model;

import java.util.List;

public class Student {
    private String name;
    private String school;
    private String startingDate;
    private String lastClass;
    private List<Lesson> lessons;

    public Student(String name, String school, String startingDate, String lastClass) {
        this.name = name;
        this.school = school;
        this.startingDate = startingDate;
        this.lastClass = lastClass;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getLastClass() {
        return lastClass;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}
