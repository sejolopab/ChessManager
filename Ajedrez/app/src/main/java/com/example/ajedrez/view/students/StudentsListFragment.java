package com.example.ajedrez.view.students;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajedrez.model.Student;
import com.example.ajedrez.network.Network;
import com.example.ajedrez.network.Observer;
import com.example.ajedrez.R;
import com.example.ajedrez.utils.GenericMethodsManager;
import com.example.ajedrez.utils.PreferenceFilters;
import com.example.ajedrez.utils.Utils;
import com.example.ajedrez.view.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StudentsListFragment extends BaseFragment implements Observer {

    //==============================================================================================
    // View objects
    //==============================================================================================

    private StudentsListener mListener;
    private RecyclerView recyclerView;
    private StudentsListAdapter adapter;

    //==============================================================================================
    // Properties
    //==============================================================================================

    private List<Student> studentsList;
    private View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentsListFragment() {
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
        view = inflater.inflate(R.layout.fragment_student_list, container, false);
        recyclerView = view.findViewById(R.id.studentsList);
        setupEventListeners();
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
        hideKeyboardFrom(requireContext(), view);
        Network.getInstance().attachStudentObserver(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (StudentsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement MyInterface ");
        }
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    public void setupEventListeners() {
        FloatingActionButton add = view.findViewById(R.id.addStudent);
        add.setOnClickListener(v -> mListener.showAddStudentScreen());
        setupSearchBar();
    }

    public void setupSearchBar() {
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
                Utils.Companion.hideKeyboard(v, requireContext());
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
