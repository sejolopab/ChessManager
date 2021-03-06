package com.example.ajedrez.View.Students;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.Utils.AlertsManager;
import com.example.ajedrez.Utils.GenericMethodsManager;
import com.example.ajedrez.View.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoStudentFragment extends Fragment {

    private StudentInfoListener mListener;
    private static final String STUDENT_PARAM = "param1";
    private Student mStudent;

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

    public void setListener(MainActivity activity) {
        this.mListener = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mStudent = (Student) bundle.getSerializable(STUDENT_PARAM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_student, container, false);
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
                mStudent.setLastClass(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        AppCompatEditText lastClass = view.findViewById(R.id.lastClassText);
        lastClass.setText(mStudent.getLastClass());

        FloatingActionButton floatingActionButton = view.findViewById(R.id.updateStudent);
        floatingActionButton.setOnClickListener(v -> updateStudent());
        return view;
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        myRef.child(mStudent.getId()).setValue(mStudent);
        mListener.onStudentInfoUpdated();
    }

    public interface StudentInfoListener {
        void onStudentInfoUpdated();
    }
}
