package com.example.ajedrez.Data;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.Model.Lesson;
import com.example.ajedrez.Model.Student;
import com.example.ajedrez.Model.Subject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {
    private static volatile DataManager sDataInstance = new DataManager();
    private List<Student> students;
    private List<Subject> subjects;
    private List<Lesson> lessons;
    private DataManager(){
        students = new ArrayList<>();
        students.add(new Student("Panfilo Rogriguez", "COPES", "2/2/2019","20/4/2019"));
        students.add(new Student("Panfila Rogriguez", "COPES", "2/2/2019","20/4/2019"));
        students.add(new Student("Shion Gamboa", "COTAI", "2/2/2019","20/4/2019"));

        subjects = new ArrayList<>();
        subjects.add(new Subject("Apertura"));
        subjects.add(new Subject("Medio juego"));
        subjects.add(new Subject("Finales"));

        List<Assistance> assists = new ArrayList<>();
        Assistance assistance = new Assistance(students.get(0),new ArrayList<Subject>() {{ add(subjects.get(0)); }});
        assists.add(assistance);

        String date = getDateTime();
        lessons = new ArrayList<>();
        lessons.add(new Lesson(date, assists));
    }

    public static DataManager getInstance() {
        return sDataInstance;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Student> getActiveStudents() {
        return students;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
