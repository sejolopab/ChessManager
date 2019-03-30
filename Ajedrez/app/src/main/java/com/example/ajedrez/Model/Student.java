package com.example.ajedrez.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable, Parcelable {
    private String id;
    private String name;
    private String school;
    private String phoneNumber;
    private String birthDay;
    private String startingDate;
    private String lastClass;
    private List<Lesson> lessons;

    public Student() {}


    //nameText, schoolText, birthDay, phoneNumber, lastClass, firstClass


    public Student(String name, String school, String phoneNumber, String birthDay, String startingDate) {
        this.name = name;
        this.school = school;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.startingDate = startingDate;
        this.lastClass = startingDate;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(school);
        dest.writeString(startingDate);
        dest.writeString(lastClass);
        dest.writeString(phoneNumber);
        dest.writeString(birthDay);
    }
}
