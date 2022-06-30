package com.example.ajedrez.view.assistance;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajedrez.model.Assistance;
import com.example.ajedrez.model.Lesson;
import com.example.ajedrez.model.Student;
import com.example.ajedrez.network.NetworkManager;
import com.example.ajedrez.network.Observer;
import com.example.ajedrez.R;
import com.example.ajedrez.utils.AlertsManager;
import com.example.ajedrez.utils.PreferenceFilters;
import com.example.ajedrez.utils.Utils;
import com.example.ajedrez.view.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StudentsAssistListFragment extends BaseFragment implements Observer {

    //==============================================================================================
    // View objects
    //==============================================================================================

    private StudentsAssistanceListener mListener;
    private AssistanceListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Assistance> assistanceList;
    private View view;

    //==============================================================================================
    // Constructors
    //==============================================================================================

    public StudentsAssistListFragment() {
        this.assistanceList = new ArrayList<>();
    }

    public static StudentsAssistListFragment newInstance() {
        return new StudentsAssistListFragment();
    }

    //==============================================================================================
    // Lifecycle
    //==============================================================================================

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assistance_list, container, false);
        recyclerView = view.findViewById(R.id.studentsAssistanceList);
        FloatingActionButton saveAssistance = view.findViewById(R.id.saveAssistance);
        saveAssistance.setOnClickListener(v -> saveAssistance());
        if (NetworkManager.getInstance().getAssistance().size() == 0) {
            for (Student student : NetworkManager.getInstance().getStudentList()) {
                assistanceList.add(new Assistance(student));
            }
        } else {
            assistanceList = NetworkManager.getInstance().getAssistance();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AssistanceListAdapter(assistanceList, mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        setupEventListeners();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (StudentsAssistanceListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement MyInterface ");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkManager.getInstance().attachStudentObserver(this);
        NetworkManager.getInstance().attachAssistanceObserver(this);
        hideKeyboardFrom(requireContext(), view);
    }

    @Override
    public void onStop() {
        super.onStop();
        NetworkManager.getInstance().detachStudentObserver(this);
        NetworkManager.getInstance().detachAssistanceObserver(this);
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    private void setupEventListeners() {
        LinearLayout contentLayout = view.findViewById(R.id.contentLayout);
        contentLayout.setOnClickListener(v -> {
            hideKeyboardFrom(requireContext(), view);
        });
        setupSearchBar();
        setupSwipeListener();
    }

    private void setupSearchBar() {
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
                Utils.Companion.hideKeyboard(v, requireContext());
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
                        tStudent.setAssisted(swipeDir == 4);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public List<Assistance> filter(String text) {
        List<Assistance> filteredList = new ArrayList<>();

        for (Assistance item : assistanceList) {
            Student student = item.getStudent();

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

    private void updateAssistanceList() {
        if (assistanceList.size() > 0) {
            assistanceList = NetworkManager.getInstance().getAssistance();
            loadAssistanceList();
            if (assistanceList.size() < NetworkManager.getInstance().getStudentList().size()) {
                Student tempStudent = null;
                for (Student student : NetworkManager.getInstance().getStudentList()) {
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
            } else {
                assistanceList = NetworkManager.getInstance().getAssistance();
            }
        } else {
            for (Student student : NetworkManager.getInstance().getStudentList()) {
                assistanceList.add(new Assistance(student));
            }
        }
        loadAssistanceList();
    }

    private void loadAssistanceList(){
        assistanceList = PreferenceFilters.getInstance().applyPreferenceFilters(assistanceList, getContext());
        adapter.setAssistanceList(assistanceList);
        adapter.notifyDataSetChanged();
    }

    private void saveAssistance() {
        Lesson lesson = new Lesson(Utils.Companion.getServerDateFormat(),
                System.currentTimeMillis(),assistanceList);

        NetworkManager.getInstance().saveTodayAttendance(lesson,
                //onComplete
                () -> AlertsManager.getInstance().showAlertDialog(null,
                        getString(R.string.successful),
                        getContext()),
                //onError
                () -> AlertsManager.getInstance().showAlertDialog(getString(R.string.error_title),
                        getString(R.string.error_unsuccessful),
                        getContext()));
    }

    //==============================================================================================
    // StudentNotifications / AssistanceNotification
    //==============================================================================================

    @Override
    public void update() {
        assistanceList = NetworkManager.getInstance().getAssistance();
        updateAssistanceList();
    }
}
