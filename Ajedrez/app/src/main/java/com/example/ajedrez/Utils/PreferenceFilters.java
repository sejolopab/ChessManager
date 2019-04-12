package com.example.ajedrez.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class PreferenceFilters {

    private String SCHOOL_PREF = "schoolPref";
    private String ACTIVITY_PREF = "activityFilterPref";

    private static PreferenceFilters instance = new PreferenceFilters();

    public static PreferenceFilters getInstance() {
        if(instance == null){
            instance = new PreferenceFilters();
        }
        return instance;
    }

    public List applyPreferenceFilters(List studentList, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        List filteredList;
        filteredList = schoolFilter(prefs.getString(SCHOOL_PREF, ""), studentList);
        filteredList = activityFilter(prefs.getBoolean(ACTIVITY_PREF, true), filteredList);
        return filteredList;
    }

    private List schoolFilter(String text, List studentsList) {
        List<Object> filteredList = new ArrayList<>();

        for (Object item : studentsList) {
            if (item instanceof Student) {
                if (((Student)item).getSchool() != null) {
                    if (((Student)item).getSchool().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            } else {
                if (((Assistance)item).getStudent().getSchool() != null) {
                    if (((Assistance)item).getStudent().getSchool().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }
        }

        return filteredList;
    }

    private List activityFilter(Boolean applayActivityFilter, List studentList) {
        return studentList;
    }

}
