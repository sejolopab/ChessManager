package com.example.ajedrez.network;

import androidx.annotation.NonNull;

import com.example.ajedrez.model.Assistance;
import com.example.ajedrez.model.Lesson;
import com.example.ajedrez.model.Student;
import com.example.ajedrez.utils.GenericMethodsManager;
import com.example.ajedrez.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Network implements StudentNotifications, AssistanceNotifications, LessonsNotifications {

    //==============================================================================================
    // Properties
    //==============================================================================================

    private static Network instance = new Network();
    private static final List<Student> studentList = new ArrayList<>();
    private static List<Assistance> assistanceList = new ArrayList<>();
    private static List<Lesson> lessonsList = new ArrayList<>();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference assistanceRef = database.getReference("assistance");
    public final DatabaseReference studentsRef = database.getReference("students");
    private final DatabaseReference lessonsRef = database.getReference().child("assistance");
    private final String todayDate = GenericMethodsManager.getInstance().getServerDateFormat();

    //==============================================================================================
    // Constructors
    //==============================================================================================

    public Network() {
        loadAssistance();
        loadStudents();
        loadLessons();
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

    private void loadLessons() {
        lessonsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lessonsList.clear();
                for(DataSnapshot lessonListData :snapshot.getChildren()){
                    String date = lessonListData.getKey();
                    List<Assistance> assistance = new ArrayList<>();
                    for (DataSnapshot student : lessonListData.getChildren()) {
                        Assistance value = student.getValue(Assistance.class);
                        assistance.add(value);
                    }
                    if (date != null) {
                        lessonsList.add(new Lesson(date, assistance));
                    }
                }
                notifyUpdateStudentObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        for (Assistance studentAssistance : assistanceList) {
            if (studentAssistance.getAssisted() != null) {
                if (studentAssistance.getAssisted()) {
                    studentAssistance.getStudent().setLastClass(todayDate);
                    studentsRef.child(studentAssistance.getStudent().getId())
                            .setValue(studentAssistance.getStudent());
                }
            }
        }
    }

    public void saveStudent(Student newStudent, Runnable onComplete, Runnable onError){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        myRef.push().setValue(newStudent, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                onError.run();
            } else {
                onComplete.run();
            }
        });
    }

    public void updateStudent(Student mStudent, Runnable onComplete, Runnable onError){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        myRef.child(mStudent.getId()).setValue(mStudent, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                onError.run();
            } else {
                onComplete.run();
            }
        });
    }

    public void deleteStudent(Student student, Runnable onComplete, Runnable onError) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        myRef.child(student.getId()).removeValue((error, ref) -> {
            if (error != null) {
                onError.run();
            } else {
                onComplete.run();
            }
        });
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

    public List<Lesson> getLessons() {
        return lessonsList;
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

    //==============================================================================================
    // LessonsNotifications
    //==============================================================================================

    private final List<Observer> lessonsObservers = new ArrayList<>();

    @Override
    public void attachLessonsObserver(Observer o) {
        lessonsObservers.add(o);
    }

    @Override
    public void detachLessonsObserver(Observer o) {
        lessonsObservers.remove(o);
    }

    @Override
    public void notifyUpdateLessonsObservers() {
        for(Observer o: lessonsObservers) {
            o.update();
        }
    }
}
