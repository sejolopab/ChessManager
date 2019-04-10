package com.example.ajedrez.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GenericMethodsManager {
    private static GenericMethodsManager instance = new GenericMethodsManager();

    public static GenericMethodsManager getInstance() {
        if(instance == null){
            instance = new GenericMethodsManager();
        }
        return instance;
    }

    public String getSimpleDate() {
        String stringFormat = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(stringFormat);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void openCalendar(AppCompatEditText appCompatEditText, Context context) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        Editable text = appCompatEditText.getText();
        if(text != null){
            if (text.length() != 0) {
                String date = text.toString();
                List<String> splitDate = Arrays.asList(date.split("/"));
                dayOfMonth = Integer.valueOf(splitDate.get(0));
                month = Integer.valueOf(splitDate.get(1)) - 1;
                year = Integer.valueOf(splitDate.get(2));
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (datePicker, year1, month1, day) ->
                        appCompatEditText.setText(day + "/" + (month1 + 1) + "/" + year1), year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void sendMessage(Activity activity) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String text = " message you want to share..";
        // change with required  application package

        intent.setPackage("com.whatsapp");
        if (intent != null) {
            intent.putExtra(Intent.EXTRA_TEXT, text);//
            activity.startActivity(Intent.createChooser(intent, text));
        } else {
            Toast.makeText(activity, "App not found", Toast.LENGTH_SHORT).show();
        }
    }
}
