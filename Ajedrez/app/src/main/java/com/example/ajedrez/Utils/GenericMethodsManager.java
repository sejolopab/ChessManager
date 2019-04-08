package com.example.ajedrez.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.DoubleAccumulator;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

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
