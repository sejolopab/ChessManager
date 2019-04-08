package com.example.ajedrez.Model;

public class Subject {
    String title;
    String fileName;

    //DO NOT REMOVE
    public Subject() {}

    public Subject(String title, String fileName) {
        this.title = title;
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }
}
