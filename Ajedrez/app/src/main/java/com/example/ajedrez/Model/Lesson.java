package com.example.ajedrez.Model;

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

    public int getAssistance() {
        int assists = 0;
        for (Assistance student:assistance) {
            if (student.assisted == null)
                continue;
            if (student.assisted)
                assists += 1;
        }
        return assists;
    }
}
