package com.example.ajedrez.view.students;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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

public class InfoStudentFragment extends BaseFragment {

    //==============================================================================================
    // Properties
    //==============================================================================================
    
    private static final String STUDENT_PARAM = "param1";
    private StudentUpdatedListener mListener;
    private Student mStudent;
    private View view;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param student Parameter 1.
     * @return A new instance of fragment InfoStudentFragment.
     */
    public static InfoStudentFragment newInstance(Student student) {
        InfoStudentFragment fragment = new InfoStudentFragment();
        Bundle args = new Bundle();
        args.putSerializable(STUDENT_PARAM, student);
        fragment.setArguments(args);
        return fragment;
    }

    //==============================================================================================
    // Lifecycle
    //==============================================================================================
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mStudent = (Student) bundle.getSerializable(STUDENT_PARAM);
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_info, container, false);
        AppCompatEditText nameText = view.findViewById(R.id.nameText);
        nameText.setText(mStudent.getName());
        nameText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                mStudent.setName(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        AppCompatEditText schoolText = view.findViewById(R.id.schoolText);
        schoolText.setText(mStudent.getSchool());
        schoolText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                mStudent.setSchool(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        AppCompatEditText phoneNumber = view.findViewById(R.id.phoneText);
        phoneNumber.setText(mStudent.getPhoneNumber());
        phoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                mStudent.setPhoneNumber(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        AppCompatEditText birthDay = view.findViewById(R.id.birthDateText);
        birthDay.setText(mStudent.getBirthDay());
        birthDay.setOnClickListener(v ->
                GenericMethodsManager.getInstance().openCalendar(birthDay, getContext()));
        birthDay.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                mStudent.setBirthDay(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        AppCompatEditText firstClass = view.findViewById(R.id.firstClassText);
        firstClass.setText(mStudent.getStartingDate());
        firstClass.setOnClickListener(v ->
                GenericMethodsManager.getInstance().openCalendar(firstClass, getContext()));
        firstClass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                mStudent.setStartingDate(s.toString());
                mStudent.setLastAttendance(Utils.Companion.getLongTimeStamp(s.toString()));
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        AppCompatEditText lastClass = view.findViewById(R.id.lastClassText);
        lastClass.setText(Utils.Companion.getStringTimeStamp(mStudent.getLastAttendance()));

        CheckBox isActiveCheckbox = view.findViewById(R.id.isActiveCheckBox);
        isActiveCheckbox.setChecked(mStudent.getActive());
        isActiveCheckbox.setOnClickListener(view1 -> mStudent.setActive(isActiveCheckbox.isChecked()));

        FloatingActionButton floatingActionButton = view.findViewById(R.id.updateStudent);
        floatingActionButton.setOnClickListener(v -> {
            hideKeyboardFrom(requireContext(),view);
            updateStudent();
        });

        Button deleteButton = view.findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(v -> {
            hideKeyboardFrom(requireContext(),view);
            AlertsManager.getInstance().showYesOrNoDialog("Eliminar estudiante",
                    "Esta seguro de desea borrar este estudainte?",
                    getContext(),
                    this::deleteStudent, //Yes button action
                    ()->{});//No button action
        });

        LinearLayout contentLayout = view.findViewById(R.id.contentLayout);
        contentLayout.setOnClickListener(v -> {
            hideKeyboardFrom(requireContext(),view);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboardFrom(requireContext(), view);
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

    //==============================================================================================
    // Methods
    //==============================================================================================

    private void deleteStudent(){
        NetworkManager.getInstance().deleteStudent(mStudent,
                //onComplete
                ()-> AlertsManager.getInstance().showAlertDialogWithAction(null,
                        getString(R.string.successful),
                        getContext(),
                        ()->mListener.returnToList()),
                //onError
                ()-> AlertsManager.getInstance().showAlertDialog(getString(R.string.error_title),
                        getString(R.string.error_unsuccessful),
                        getContext()));
    }
    
    private void updateStudent(){
        if (mStudent.getName() == null) {
            AlertsManager.getInstance().showAlertDialog(getString(R.string.error_title), getString(R.string.error_name_required), getContext());
            return;
        }
        if (mStudent.getName().isEmpty()) {
            AlertsManager.getInstance().showAlertDialog(getString(R.string.error_title), getString(R.string.error_name_required), getContext());
            return;
        }

        NetworkManager.getInstance().updateStudent(mStudent,
                //onComplete
                ()-> AlertsManager.getInstance().showAlertDialog(null,
                        getString(R.string.successful),
                        getContext()),
                //onError
                ()-> AlertsManager.getInstance().showAlertDialog(getString(R.string.error_title),
                        getString(R.string.error_unsuccessful),
                        getContext()));
    }
}
