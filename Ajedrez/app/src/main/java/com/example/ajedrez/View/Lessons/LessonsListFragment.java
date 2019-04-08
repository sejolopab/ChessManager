package com.example.ajedrez.View.Lessons;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.Model.Lesson;
import com.example.ajedrez.R;
import com.example.ajedrez.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LessonsListFragment extends Fragment {

    private LessonsListener mListener;
    private RecyclerView recyclerView;
    private LessonsListAdapter adapter;
    private List<Lesson> lessonList;

    public void setListener (MainActivity activity) {
        this.mListener = activity;
    }

    public static LessonsListFragment newInstance() {
        return new LessonsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons_list, container, false);
        recyclerView = view.findViewById(R.id.lessonsList);
        lessonList = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new LessonsListAdapter(lessonList, mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        loadAssistance();
    }

    private void loadAssistance() {
        Query studentsQuery = FirebaseDatabase.getInstance().getReference().child("assistance");
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Lesson> lessonsList = new ArrayList<>();
                for(DataSnapshot assistanceList :dataSnapshot.getChildren()){
                    String date = assistanceList.getKey();
                    List<Assistance> assistants = new ArrayList<>();
                    for(DataSnapshot student:assistanceList.getChildren()){
                        Assistance value = student.getValue(Assistance.class);
                        assistants.add(value);
                    }
                    lessonsList.add(new Lesson(date,assistants));
                }
                adapter.setLessonsList(lessonsList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface LessonsListener {
    }
}
