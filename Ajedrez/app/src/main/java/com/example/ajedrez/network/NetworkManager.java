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

public class NetworkManager implements StudentNotifications, AssistanceNotifications, LessonsNotifications {

    //==============================================================================================
    // Properties
    //==============================================================================================

    private static NetworkManager instance = new NetworkManager();
    private static final List<Student> studentList = new ArrayList<>();
    private static final List<Lesson> lessonsList = new ArrayList<>();
    private static List<Assistance> assistanceList = new ArrayList<>();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference assistanceRef = database.getReference("assistance");
    public final DatabaseReference studentsRef = database.getReference("students");
    private final DatabaseReference lessonsRef = database.getReference().child("assistance");
    private final String todayDate = GenericMethodsManager.getInstance().getServerDateFormat();

    //==============================================================================================
    // Constructors
    //==============================================================================================

    public NetworkManager() {
        loadAssistance();
        loadStudents();
        loadLessons();
    }

    public static NetworkManager getInstance() {
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    //==============================================================================================
    // Requests
    //==============================================================================================

    public void updateupdateupdate() {
        for (Student student : studentList) {
            //student.setLastAttendance(Utils.Companion.getLongTimeStamp(student.getLastClass()));
            studentsRef.child(student.getId()).setValue(student);
        }
    }

    private void loadAssistance() {
        Query studentsQuery = assistanceRef.child(todayDate);
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assistanceList.clear();
                Lesson value = dataSnapshot.getValue(Lesson.class);
                if (value != null) {
                    assistanceList = value.getAssistance();
                    notifyUpdateAssistanceObservers();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadStudents() {
        studentsRef.orderByChild("lastAttendance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();
                for(DataSnapshot data :dataSnapshot.getChildren()){
                    Student student = data.getValue(Student.class);
                    if (student == null || !student.getActive())
                        continue;
                    student.setId(data.getKey());
                    studentList.add(0, student);
                }

                notifyUpdateStudentObservers();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadLessons() {
        lessonsRef.orderByChild("date").limitToLast(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lessonsList.clear();
                for(DataSnapshot lesson :snapshot.getChildren()){
                    Lesson value = lesson.getValue(Lesson.class);
                    lessonsList.add(0, value);
                }
                notifyUpdateLessonsObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateAttendance(Lesson lesson, Long oldDate, Runnable onComplete, Runnable onError) {
        /*assistanceRef.child(String.valueOf(lesson.getDate())).setValue(lesson,
                (databaseError, databaseReference) -> {
            if (databaseError != null) {
                onError.run();
            } else {
                onComplete.run();
            }
        });

        if (lesson.getDate() > oldDate) {
            //update students lastclass
        }*/
    }

    public void saveTodayAttendance(Lesson newLesson, Runnable onComplete, Runnable onError) {
        assistanceRef.child(todayDate).setValue(newLesson, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                onError.run();
            } else {
                onComplete.run();
            }
        });

        for (Assistance studentAssistance : newLesson.getStudents()) {
            Student student = studentAssistance.getStudent();
            student.setLastAttendance(newLesson.getDate());
            studentsRef.child(student.getId()).setValue(student);
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
