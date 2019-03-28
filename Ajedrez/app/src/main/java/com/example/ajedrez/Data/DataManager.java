package com.example.ajedrez.Data;

import com.example.ajedrez.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static volatile DataManager sDataInstance = new DataManager();
    private List<Student> students, subjects;

    private DataManager(){
        students = new ArrayList<>();
        students.add(new Student("Panfilo Rogriguez", "COPES", "2/2/2019","20/4/2019"));
        students.add(new Student("Panfila Rogriguez", "COPES", "2/2/2019","20/4/2019"));
        students.add(new Student("Shion Gamboa", "COTAI", "2/2/2019","20/4/2019"));
    }

    public static DataManager getInstance() {
        return sDataInstance;
    }
    public List<Student> getSubjects() {
        return subjects;
    }

    public List<Student> getActiveStudents() {
        return students;
    }
}
