package com.example.ajedrez.View.Students;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.Utils.DateManager;
import com.example.ajedrez.View.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewStudentListener} interface
 * to handle interaction events.
 * Use the {@link NewStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewStudentFragment extends Fragment {

    private NewStudentListener mListener;
    private AppCompatEditText nameText, schoolText, firstClassText;

    public NewStudentFragment() {
        // Required empty public constructor
    }

    public void setListener(MainActivity activity) {
        this.mListener = activity;
    }

    public static NewStudentFragment newInstance() {
        return new NewStudentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_student, container, false);
        FloatingActionButton add = view.findViewById(R.id.saveStudent);
        add.setOnClickListener(v -> createUser());
        nameText = view.findViewById(R.id.nameText);
        schoolText = view.findViewById(R.id.schoolText);
        firstClassText = view.findViewById(R.id.firstClassText);
        firstClassText.setHint(DateManager.getInstance().getSimpleDate());
        return view;
    }

    private void createUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("students");
        String date = firstClassText.getText().toString().equals("") ?
                DateManager.getInstance().getSimpleDate() : firstClassText.getText().toString();
        Student student = new Student(nameText.getText().toString(), schoolText.getText().toString(), date,date);
        myRef.push().setValue(student);
        mListener.onStudentCreated();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface NewStudentListener {
        void onStudentCreated();
    }
}
