package com.example.ajedrez.View.Subjects;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Subject;
import com.example.ajedrez.R;
import com.example.ajedrez.View.BaseFragment;
import com.example.ajedrez.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubjectsListFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SubjectsListAdapter adapter;
    private SubjectsListener mListener;
    private List<Subject> subjectList;

    public static SubjectsListFragment newInstance() {
        return new SubjectsListFragment();
    }

    public void setListener (MainActivity activity) {
        mListener = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subjects_list, container, false);
        recyclerView = view.findViewById(R.id.subjectsList);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SubjectsListAdapter(new ArrayList<>(), mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        loadSubjects();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboardFrom(Objects.requireNonNull(getContext()), Objects.requireNonNull(getView()));
    }

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
