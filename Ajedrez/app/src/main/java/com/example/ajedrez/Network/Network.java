package com.example.ajedrez.Network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.Model.Student;
import com.example.ajedrez.Utils.AlertsManager;
import com.example.ajedrez.Utils.GenericMethodsManager;
import com.example.ajedrez.Utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Network implements StudentNotifications, AssistanceNotifications {

    //==============================================================================================
    // Properties
    //==============================================================================================

    private static Network instance = new Network();
    private static final List<Student> studentList = new ArrayList<>();
    private static List<Assistance> assistanceList = new ArrayList<>();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference assistanceRef = database.getReference("assistance");
    private final DatabaseReference studentsRef = database.getReference("students");

    private final String todayDate = GenericMethodsManager.getInstance().getServerDateFormat();

    //==============================================================================================
    // Constructors
    //==============================================================================================

    public Network() {
        loadAssistance();
        loadStudents();
    }

    public static Network getInstance() {
        if(instance == null){
            instance = new Network();
        }
        return instance;
    }

    //==============================================================================================
    // Requests
    //==============================================================================================

    private void loadAssistance() {
        Query studentsQuery = assistanceRef.child(todayDate);
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assistanceList.clear();
                for(DataSnapshot student :dataSnapshot.getChildren()){
                    Assistance value = student.getValue(Assistance.class);
                    assistanceList.add(value);
                }
                notifyUpdateAssistanceObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadStudents() {
        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                for(DataSnapshot data :dataSnapshot.getChildren()){
                    Student newStudent = data.getValue(Student.class);
                    if (newStudent == null || !newStudent.getActive())
                        continue;
                    newStudent.setId(data.getKey());
                    studentList.add(newStudent);
                }
                notifyUpdateStudentObservers();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveAssistance(List<Assistance> newAssistance, Runnable onComplete, Runnable onError) {
        assistanceRef.child(todayDate).setValue(newAssistance, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                onError.run();
            } else {
                onComplete.run();
            }
        });

        for (Assistance studentAssistance : newAssistance) {
            if (studentAssistance.getAssisted() != null) {
                if (studentAssistance.getAssisted()) {
                    Objects.requireNonNull(studentAssistance.getStudent()).setLastClass(todayDate);
                    studentsRef.child(Objects.requireNonNull(studentAssistance.getStudent().getId()))
                            .setValue(studentAssistance.getStudent());
                }
            }
        }

    }

    //==============================================================================================
    // Getters/Setters
    //==============================================================================================

    public List<Student> getStudentList() {
        return studentList;
    }

    public List<Assistance> getAssistance() {
        return assistanceList;
    }

    //==============================================================================================
    // StudentNotifications
    //==============================================================================================

    private final List<Observer> studentObservers = new ArrayList<>();

    @Override
    public void attachStudentObserver(Observer o) {
        studentObservers.add(o);
    }

    @Override
    public void detachStudentObserver(Observer o) {
        studentObservers.remove(o);
    }

    @Override
    public void notifyUpdateStudentObservers() {
        for(Observer o: studentObservers) {
            o.update();
        }
    }

    //==============================================================================================
    // AssistanceNotifications
    //==============================================================================================

    private final List<Observer> assistanceObservers = new ArrayList<>();

    @Override
    public void attachAssistanceObserver(Observer o) {
        assistanceObservers.add(o);
    }

    @Override
    public void detachAssistanceObserver(Observer o) {
        assistanceObservers.remove(o);
    }

    @Override
    public void notifyUpdateAssistanceObservers() {
        for(Observer o: assistanceObservers) {
            o.update();
        }
    }
}
