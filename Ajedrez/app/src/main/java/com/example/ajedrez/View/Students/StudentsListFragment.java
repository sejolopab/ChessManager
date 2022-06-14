package com.example.ajedrez.View.Students;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Student;
import com.example.ajedrez.Network.Network;
import com.example.ajedrez.Network.Observer;
import com.example.ajedrez.R;
import com.example.ajedrez.Utils.GenericMethodsManager;
import com.example.ajedrez.Utils.PreferenceFilters;
import com.example.ajedrez.Utils.Utils;
import com.example.ajedrez.View.BaseFragment;
import com.example.ajedrez.View.MainActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentsListFragment extends BaseFragment implements Observer {

    //==============================================================================================
    // View objects
    //==============================================================================================

    private StudentsListener mListener;
    private RecyclerView recyclerView;
    private StudentsListAdapter adapter;
    private AppCompatEditText searchText;

    //==============================================================================================
    // Properties
    //==============================================================================================

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

    //==============================================================================================
    // Lifecycle
    //==============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        recyclerView = view.findViewById(R.id.studentsList);
        setupEventListeners(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentsList = Network.getInstance().getStudentList();
        adapter = new StudentsListAdapter(studentsList, mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        loadStudents();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboardFrom(Objects.requireNonNull(getContext()), Objects.requireNonNull(getView()));
        Network.getInstance().attachStudentObserver(this);
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    public void setupEventListeners(@NonNull View view) {
        FloatingActionButton add = view.findViewById(R.id.addStudent);
        add.setOnClickListener(v -> mListener.showAddStudentScreen());
        setupSearchBar(view);
    }

    public void setupSearchBar(@NonNull View view) {
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
                if (!s.toString().isEmpty()) {
                    List<Student> filteredList = GenericMethodsManager.getInstance().filter(s.toString(), studentsList);
                    filteredList = PreferenceFilters.getInstance().applyPreferenceFilters(filteredList, getContext());
                    adapter.setStudentsList(filteredList);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setStudentsList(studentsList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        searchText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Utils.Companion.hideKeyboard(v, getContext());
            }
        });
    }

    public void loadStudents() {
        adapter.setStudentsList(studentsList);
        adapter.notifyDataSetChanged();
    }

    //==============================================================================================
    // StudentNotification
    //==============================================================================================

    @Override
    public void update() {
        studentsList = Network.getInstance().getStudentList();
        loadStudents();
    }
}
