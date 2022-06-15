package com.example.ajedrez.View.Students;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajedrez.R;
import com.example.ajedrez.Model.Student;

import java.util.List;

public class StudentsListAdapter extends RecyclerView.Adapter<StudentsListAdapter.ViewHolder> {

    private List<Student> mStudentsList;
    private final StudentsListener mListener;

    StudentsListAdapter(List items, StudentsListener listener) {
        mStudentsList = (List<Student>)items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mStudentsList.get(position);
        holder.mName.setText(mStudentsList.get(position).getName());
        holder.mLastClass.setText(mStudentsList.get(position).getLastClass());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.showStudentInfoScreen(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStudentsList.size();
    }

    void setStudentsList(List<Student> newList) {
        this.mStudentsList = newList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mName;
        final TextView mLastClass;
        Student mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.item_number);
            mLastClass = view.findViewById(R.id.content);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mLastClass.getText() + "'";
        }
    }
}
