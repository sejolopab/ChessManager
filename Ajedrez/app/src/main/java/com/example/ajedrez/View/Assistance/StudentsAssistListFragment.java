package com.example.ajedrez.View.Assistance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.Model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.Utils.GenericMethodsManager;
import com.example.ajedrez.Utils.PreferenceFilters;
import com.example.ajedrez.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentsAssistListFragment extends Fragment {

    private StudentsAssistanceListener mListener;
    private AssistanceListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Assistance> assistanceList;
    private String todayDate = GenericMethodsManager.getInstance().getSimpleDate();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference assistanceRef = database.getReference("assistance");
    DatabaseReference studentsRef = database.getReference("students");
    List<Student> studentList = new ArrayList<>();

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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AssistanceListAdapter(assistanceList, mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        AppCompatEditText searchText = view.findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                List<Assistance> filteredList = filter(s.toString());
                filteredList = PreferenceFilters.getInstance().applyPreferenceFilters(filteredList, getContext());
                adapter.setAssistanceList(filteredList);
                adapter.notifyDataSetChanged();
            }
        });
        setupSwipeListener();
        loadAssistance();
    }

    public List<Assistance> filter(String text) {
        List<Assistance> filteredList = new ArrayList<>();

        for (Assistance item : assistanceList) {
            if (item.getStudent().getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
            if (item.getStudent().getSchool() != null) {
                if (item.getStudent().getSchool().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            if (item.getStudent().getBirthDay() != null) {
                if (item.getStudent().getBirthDay().contains(text)) {
                    filteredList.add(item);
                }
            }
        }

        return filteredList;
    }

    private void loadAssistance() {
        Query studentsQuery = assistanceRef.child(todayDate);
        studentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assistanceList = new ArrayList<>();
                for(DataSnapshot student :dataSnapshot.getChildren()){
                    Assistance value = student.getValue(Assistance.class);
                    assistanceList.add(value);
                }
                if (assistanceList.size() > 0) {
                    assistanceList = PreferenceFilters.getInstance().applyPreferenceFilters(assistanceList, getContext());
                    adapter.setAssistanceList(assistanceList);
                    adapter.notifyDataSetChanged();
                } else {
                    loadStudents();
                }
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
                assistanceList = new ArrayList<>();
                for(DataSnapshot data :dataSnapshot.getChildren()){
                    Student newStudent = data.getValue(Student.class);
                    if (newStudent == null)
                        continue;
                    newStudent.setId(data.getKey());
                    assistanceList.add(new Assistance(newStudent));
                    studentList.add(newStudent);
                }
                assistanceList = PreferenceFilters.getInstance().applyPreferenceFilters(assistanceList, getContext());
                adapter.setAssistanceList(assistanceList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupSwipeListener() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                 int swipeDir) {
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
        assistanceRef.child(todayDate).setValue(assistanceList);
        for (Assistance studentAssistance : assistanceList) {
            if (studentAssistance.assisted == null)
                continue;
            if (studentAssistance.assisted) {
                studentAssistance.getStudent().setLastClass(todayDate);
                studentsRef.child(studentAssistance.getStudent().getId()).setValue(studentAssistance.getStudent());
            }
        }
        mListener.onAssistanceListSaved();
    }

    public interface StudentsAssistanceListener {
        void onAssistanceListSaved();
    }
}
