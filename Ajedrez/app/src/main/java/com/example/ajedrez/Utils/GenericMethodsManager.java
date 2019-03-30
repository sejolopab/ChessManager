package com.example.ajedrez.Utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GenericMethodsManager {
    private static GenericMethodsManager instance = new GenericMethodsManager();

    public static GenericMethodsManager getInstance() {
        if(instance == null){
            instance = new GenericMethodsManager();
        }
        return instance;
    }

    public String getSimpleDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void openCalendar(AppCompatEditText appCompatEditText, Context context) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (datePicker, year1, month1, day) ->
                        appCompatEditText.setText(day + "/" + (month1 + 1) + "/" + year1), year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}
