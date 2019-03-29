package com.example.ajedrez.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {
    private static DateManager instance = new DateManager();

    public static DateManager getInstance() {
        if(instance == null){
            instance = new DateManager();
        }
        return instance;
    }

    public String getSimpleDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getLessonDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh a");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
