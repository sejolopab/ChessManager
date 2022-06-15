package com.example.ajedrez.view.subjects;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajedrez.model.Subject;
import com.example.ajedrez.R;
import com.example.ajedrez.view.BaseFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubjectsListFragment extends BaseFragment {

    //==============================================================================================
    // View Objects
    //==============================================================================================

    private RecyclerView recyclerView;
    private SubjectsListAdapter adapter;
    private SubjectsListener mListener;
    private List<Subject> subjectList;

    public static SubjectsListFragment newInstance() {
        return new SubjectsListFragment();
    }

    //==============================================================================================
    // Lifecycle
    //==============================================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subjects_list, container, false);
        recyclerView = view.findViewById(R.id.subjectsList);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SubjectsListAdapter(new ArrayList<>(), mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        loadSubjects();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboardFrom(getContext(), getView());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (SubjectsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement MyInterface ");
        }
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    public void loadSubjects() {
        Query studentsQuery = FirebaseDatabase.getInstance().getReference().child("subjects").child("Principiante");
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjectList = new ArrayList<>();
                for(DataSnapshot subject :dataSnapshot.getChildren()){
                    Subject newSubject = subject.getValue(Subject.class);
                    subjectList.add(newSubject);
                }
                adapter.setSubjectsList(subjectList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface SubjectsListener {
        public void showSubjectReader(String fileName);
    }
}
