package com.example.ajedrez.view.students;

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
import com.example.ajedrez.model.Student;
import com.example.ajedrez.network.NetworkManager;
import com.example.ajedrez.R;
import com.example.ajedrez.utils.AlertsManager;
import com.example.ajedrez.utils.GenericMethodsManager;
import com.example.ajedrez.utils.Utils;
import com.example.ajedrez.view.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewStudentFragment extends BaseFragment {

    //==============================================================================================
    // Properties
    //==============================================================================================

    private final Student newStudent =  new Student();
    private StudentUpdatedListener mListener;
    private View view;

    public static NewStudentFragment newInstance() {
        return new NewStudentFragment();
    }

    //==============================================================================================
    // LifeCycle
    //==============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_student_new, container, false);
        FloatingActionButton add = view.findViewById(R.id.saveStudent);
        add.setOnClickListener(v -> createStudent());
        AppCompatEditText nameText = view.findViewById(R.id.nameText);
        nameText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                newStudent.setName(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        AppCompatEditText phoneNumberText = view.findViewById(R.id.phoneText);
        phoneNumberText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                newStudent.setPhoneNumber(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        AppCompatEditText birthDateText = view.findViewById(R.id.birthDateText);
        birthDateText.setOnClickListener(v ->
                GenericMethodsManager.getInstance().openCalendar(birthDateText, getContext()));
        birthDateText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                newStudent.setBirthDay(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        AppCompatEditText schoolText = view.findViewById(R.id.schoolText);
        schoolText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                newStudent.setSchool(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        String autoGeneratedDate = GenericMethodsManager.getInstance().getSimpleDate();
        AppCompatEditText firstClassText = view.findViewById(R.id.firstClassText);
        firstClassText.setText(autoGeneratedDate);
        newStudent.setStartingDate(autoGeneratedDate);
        newStudent.setLastAttendance(System.currentTimeMillis());
        firstClassText.setOnClickListener(v ->
                GenericMethodsManager.getInstance().openCalendar(firstClassText, getContext()));
        firstClassText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                newStudent.setStartingDate(s.toString());
                newStudent.setLastAttendance(Utils.Companion.getLongTimeStamp(s.toString()));
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        LinearLayout contentLayout = view.findViewById(R.id.contentLayout);
        contentLayout.setOnClickListener(v -> hideKeyboardFrom(requireContext(),view));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (StudentUpdatedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement MyInterface ");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboardFrom(requireContext(), view);
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    private void createStudent(){
        if (newStudent.getName() == null) {
            AlertsManager.getInstance().showAlertDialog(getString(R.string.error_title), getString(R.string.error_name_required), getContext());
            return;
        }
        if (newStudent.getName().isEmpty()) {
            AlertsManager.getInstance().showAlertDialog(getString(R.string.error_title), getString(R.string.error_name_required), getContext());
            return;
        }
        NetworkManager.getInstance().saveStudent(newStudent,
                //onComplete
                ()-> AlertsManager.getInstance().showAlertDialogWithAction(null,
                        getString(R.string.successful),
                        getContext(),
                        () -> this.mListener.returnToList()),
                //onError
                ()-> AlertsManager.getInstance().showAlertDialog(getString(R.string.error_title),
                        getString(R.string.error_unsuccessful),
                        getContext()));
    }
}