package com.example.ajedrez.Model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String school;
    private String startingDate;
    private String lastClass;
    private List<Lesson> lessons;

    public Student() {}

    public Student(String name, String school, String startingDate, String lastClass) {
        this.name = name;
        this.school = school;
        this.startingDate = startingDate;
        this.lastClass = lastClass;
        this.lessons = new ArrayList<>();
    }

    public Student(String name, String school, String startingDate, String lastClass, List<Lesson> lessons) {
        this.name = name;
        this.school = school;
        this.startingDate = startingDate;
        this.lastClass = lastClass;
        this.lessons = lessons;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public void setLastClass(String lastClass) {
        this.lastClass = lastClass;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
