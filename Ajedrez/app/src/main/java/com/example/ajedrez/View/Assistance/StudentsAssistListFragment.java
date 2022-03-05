package com.example.ajedrez.View.Assistance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.Model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.Utils.GenericMethodsManager;
import com.example.ajedrez.Utils.PreferenceFilters;
import com.example.ajedrez.Utils.Utils;
import com.example.ajedrez.View.BaseFragment;
import com.example.ajedrez.View.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentsAssistListFragment extends BaseFragment {

    private StudentsAssistanceListener mListener;
    private AssistanceListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Assistance> assistanceList;
    private final String todayDate = GenericMethodsManager.getInstance().getServerDateFormat();

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
                if (!s.toString().isEmpty()) {
                    List<Assistance> filteredList = filter(s.toString());
                    filteredList = PreferenceFilters.getInstance().applyPreferenceFilters(filteredList, getContext());
                    adapter.setAssistanceList(filteredList);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setAssistanceList(assistanceList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        searchText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Utils.Companion.hideKeyboard(v, Objects.requireNonNull(getContext()));
            }
        });

        setupSwipeListener();
        loadAssistance();

        LinearLayout contentLayout = view.findViewById(R.id.contentLayout);
        contentLayout.setOnClickListener(v -> {
            hideKeyboardFrom(Objects.requireNonNull(getContext()), view);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboardFrom(Objects.requireNonNull(getContext()), Objects.requireNonNull(getView()));
    }

    public List<Assistance> filter(String text) {
        List<Assistance> filteredList = new ArrayList<>();

        for (Assistance item : assistanceList) {

            Student student = item.getStudent();
            if (student == null) { continue; }

            if (student.getName() != null) {
                if (student.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                    continue;
                }
            }

            if (student.getSchool() != null) {
                if (student.getSchool().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                    continue;
                }
            }

            if (student.getBirthDay() != null) {
                if (student.getBirthDay().contains(text)) {
                    filteredList.add(item);
                }
            }
        }

        return filteredList;
    }

    public List<Assistance> filterActiveStudents() {
        List<Assistance> filteredList = new ArrayList<>();

        for (Assistance item : assistanceList) {
            Student student = item.getStudent();
            if (student != null || student.getActive()) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }

    private void loadAssistance() {

        loadStudents();

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
                    if (assistanceList.size() < studentList.size()) {
                        Student tempStudent = null;
                        for (Student student : studentList) {
                            for (Assistance assistance : assistanceList) {
                                if (student.getId().equals(assistance.getStudent().getId())) {
                                    tempStudent = student;
                                    break;
                                }
                            }
                            if (tempStudent == null) {
                                assistanceList.add(new Assistance(student));
                                saveAssistance();
                            }
                        }
                        loadAssistanceList();
                    } else {
                        loadAssistanceList();
                    }
                } else {
                    for (Student student : studentList) {
                        assistanceList.add(new Assistance(student));
                    }
                    loadAssistanceList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadAssistanceList(){
        assistanceList = PreferenceFilters.getInstance().applyPreferenceFilters(assistanceList, getContext());
        adapter.setAssistanceList(assistanceList);
        adapter.notifyDataSetChanged();
    }

    private void loadStudents() {
        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assistanceList = new ArrayList<>();
                for(DataSnapshot data :dataSnapshot.getChildren()){
                    Student newStudent = data.getValue(Student.class);
                    if (newStudent == null || !newStudent.getActive())
                        continue;
                    newStudent.setId(data.getKey());
                    //assistanceList.add(new Assistance(newStudent));
                    studentList.add(newStudent);
                }
                //assistanceList = PreferenceFilters.getInstance().applyPreferenceFilters(assistanceList, getContext());
                //adapter.setAssistanceList(assistanceList);
                //adapter.notifyDataSetChanged();
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
                AssistanceListAdapter.AssistanceViewHolder vHolder = (AssistanceListAdapter.AssistanceViewHolder) viewHolder;
                Assistance tStudent = vHolder.getItem();
                for (Assistance assistance: assistanceList) {
                    if (assistance.getStudent().getName().equals(tStudent.getStudent().getName())) {
                        //assistanceList.remove(assistance);
                        //assistanceList.add(tStudent);

                        if (swipeDir == 4) {
                            tStudent.setAssisted(true);
                        } else {
                            tStudent.setAssisted(false);
                        }

                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void saveAssistance() {
        assistanceRef.child(todayDate).setValue(assistanceList);
        for (Assistance studentAssistance : assistanceList) {
            if (studentAssistance.getAssisted() != null) {
                if (studentAssistance.getAssisted()) {
                    Objects.requireNonNull(studentAssistance.getStudent()).setLastClass(todayDate);
                    studentsRef.child(Objects.requireNonNull(studentAssistance.getStudent().getId()))
                            .setValue(studentAssistance.getStudent());
                }
            }
        }
        mListener.onAssistanceListSaved();
    }

    public interface StudentsAssistanceListener {
        void onAssistanceListSaved();
        void onAssistanceItemClick();
    }
}
