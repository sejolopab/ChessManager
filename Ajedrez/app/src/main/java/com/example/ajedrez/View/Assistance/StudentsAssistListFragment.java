package com.example.ajedrez.View.Assistance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.Model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.Utils.DateManager;
import com.example.ajedrez.View.MainActivity;
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
 * Activities containing this fragment MUST implement the {@link StudentsAssistanceListener}
 * interface.
 */
public class StudentsAssistListFragment extends Fragment {

    private StudentsAssistanceListener mListener;
    private AssistanceListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Assistance> assistanceList;


    public void setListener(MainActivity activity) {
        this.mListener = activity;
    }


    public static StudentsAssistListFragment newInstance() {
        return new StudentsAssistListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assistance_list, container, false);
        recyclerView = view.findViewById(R.id.studentsAssistanceList);
        FloatingActionButton saveAssistance = view.findViewById(R.id.saveAssistance);
        saveAssistance.setOnClickListener(v -> saveAssistance());
        assistanceList = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AssistanceListAdapter(assistanceList, mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        setupSwipeListener();
        loadStudents();
        //adapter.setOnClickListener(this);
    }

    private void loadStudents() {
        Query studentsQuery = FirebaseDatabase.getInstance().getReference().child("students");
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assistanceList = new ArrayList<>();
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
                    assistanceList.add(new Assistance(newStudent));
                }
                adapter.setAssistanceList(assistanceList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupSwipeListener() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                Assistance tStudent = assistanceList.get(position);

                assistanceList.remove(position);
                assistanceList.add(tStudent);

                if (swipeDir == 4) {
                    tStudent.setAssisted(true);
                } else {
                    tStudent.setAssisted(false);
                }

                adapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void saveAssistance() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("assistance");
        myRef.child(DateManager.getInstance().getSimpleDate()).setValue(assistanceList);
        mListener.onAssistanceListSaved();
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
    public interface StudentsAssistanceListener {
        void onAssistanceListSaved();
    }
}
