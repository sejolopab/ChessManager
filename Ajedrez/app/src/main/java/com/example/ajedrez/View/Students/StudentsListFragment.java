package com.example.ajedrez.View.Students;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.Utils.GenericMethodsManager;
import com.example.ajedrez.View.MainActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentsListFragment extends Fragment {

    private StudentsListener mListener;
    private RecyclerView recyclerView;
    private StudentsListAdapter adapter;
    private List<Student> studentsList;
    private AppCompatEditText searchText;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentsListFragment() {
    }

    public void setListener(MainActivity activity) {
        this.mListener = activity;
    }

    public static StudentsListFragment newInstance() {
        return new StudentsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseApp.initializeApp(getContext());
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        recyclerView = view.findViewById(R.id.studentsList);
        searchText = view.findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                List<Student> filteredList = GenericMethodsManager.getInstance().filter(s.toString(), studentsList);
                adapter.setStudentsList(filteredList);
                adapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton add = view.findViewById(R.id.addStudent);
        add.setOnClickListener(v -> mListener.showAddStudentScreen());
        return view;
    }

    public void loadStudents() {
        Query studentsQuery = FirebaseDatabase.getInstance().getReference().child("students");
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentsList = new ArrayList<>();
                for(DataSnapshot data :dataSnapshot.getChildren()){
                    Student newStudent = data.getValue(Student.class);
                    newStudent.setId(data.getKey());
                    studentsList.add(newStudent);
                }
                adapter.setStudentsList(studentsList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentsList = new ArrayList<>();
        adapter = new StudentsListAdapter(studentsList, mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        loadStudents();
    }

    public interface StudentsListener {
        void showAddStudentScreen();
        void showStudentInfoScreen(Student student);
    }
}
