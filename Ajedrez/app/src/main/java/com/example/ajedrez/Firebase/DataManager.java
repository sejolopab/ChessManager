package com.example.ajedrez.Firebase;

import android.support.annotation.NonNull;

import com.example.ajedrez.Model.Student;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DataManager {

    private static DataManager instance = new DataManager();
    private FirebaseDatabase mDatabase;
    private List<Student> students;

    private DataManager(){
        initDatabase();
    }

    public static DataManager getInstance() {
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    /**
     * initialize firebase.
     */
    private void initDatabase() {
        mDatabase = FirebaseDatabase.getInstance();
        loadStudents();
    }

    public List<Student> getStudents (){
        return students;
    }

    public void loadStudents() {
        Query studentsQuery = mDatabase.getReference().child("students");
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                students = (List<Student>) dataSnapshot.child("students").getValue(List.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
