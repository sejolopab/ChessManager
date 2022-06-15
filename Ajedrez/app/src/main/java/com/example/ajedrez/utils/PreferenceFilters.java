package com.example.ajedrez.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ajedrez.model.Assistance;
import com.example.ajedrez.model.Student;

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
        List filteredList = new ArrayList();
        String school = prefs.getString(SCHOOL_PREF, "");
        Boolean activePref = prefs.getBoolean(ACTIVITY_PREF, false);

        for (Object student : studentList) {
            boolean addToFilteredList = true;
            // School filter exist
            if (school != null && !school.equals("")) {
                if (!appliesForSchoolFilter(student, school)) {
                    addToFilteredList = false;
                }
            }

            // Activity filter exist
            if (activePref) {
                if (!appliesForActivityFilter(student)) {
                    addToFilteredList = false;
                }
            }

            if (addToFilteredList) {
                filteredList.add(student);
            }
        }

        return filteredList;
    }

    private boolean appliesForSchoolFilter(Object object, String text) {
        if (object instanceof Student) {
            Student student = (Student) object;
            if (student.getSchool() != null) {
                return student.getSchool().toLowerCase().contains(text.toLowerCase());
            }
        } else {
            Assistance student = (Assistance) object;
            if (student.getStudent().getSchool() != null) {
                return student.getStudent().getSchool().toLowerCase().contains(text.toLowerCase());
            }
        }
        return false;
    }

    private boolean appliesForActivityFilter(Object object) {
        if (object instanceof Student) {
            Student student = (Student) object;
            return student.getActive();
        } else {
            Assistance student = (Assistance) object;
            return student.getStudent().getActive();
        }
    }
}
