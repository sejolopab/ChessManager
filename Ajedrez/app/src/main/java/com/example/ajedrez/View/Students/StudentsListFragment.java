package com.example.ajedrez.View.Students;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.View.MainActivity;

//import com.firebase.client.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link StudentsListener}
 * interface.
 */
public class StudentsListFragment extends Fragment {

    private StudentsListener mListener;
    private RecyclerView recyclerView;
    private StudentsListAdapter adapter;
    private List<Student> studentsList;
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
        FloatingActionButton add = view.findViewById(R.id.addStudent);
        add.setOnClickListener(v -> mListener.showAddStudentScreen());
        return view;
    }

    public void loadStudents() {
        Query studentsQuery = FirebaseDatabase.getInstance().getReference().child("students");
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentsList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    Student value = dataSnapshot1.getValue(Student.class);
                    Student newStudent = new Student();
                    String name = value.getName();
                    String school = value.getSchool();
                    String lastClass = value.getLastClass();
                    String startingDate = value.getStartingDate();
                    newStudent.setName(name);
                    newStudent.setSchool(school);
                    newStudent.setLastClass(lastClass);
                    newStudent.setStartingDate(startingDate);
                    studentsList.add(newStudent);
                }
                adapter.setStudentsList(studentsList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentsList = new ArrayList<>();
        adapter = new StudentsListAdapter(studentsList, mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        loadStudents();
        //adapter.setOnClickListener(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface StudentsListener {
        void showAddStudentScreen();
    }
}
