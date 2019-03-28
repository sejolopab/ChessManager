package com.example.ajedrez.Model;

import java.util.Date;
import java.util.List;

public class Lesson {
    private String date;
    private List<Assistance> assistance;

    public Lesson(String date, List<Assistance> assistance) {
        this.date = date;
        this.assistance = assistance;
    }

    public String getDate() {
        return date;
    }

    public List<Assistance> getAssistance() {
        return assistance;
    }
}
