package com.example.ajedrez.View.Students;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajedrez.R;
import com.example.ajedrez.View.Students.StudentsListFragment.StudentsListener;
import com.example.ajedrez.Model.Student;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Student} and makes a call to the
 * specified {@link StudentsListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class StudentsListAdapter extends RecyclerView.Adapter<StudentsListAdapter.ViewHolder> {

    private List<Student> mStudentsList;
    private final StudentsListener mListener;

    public StudentsListAdapter(List items, StudentsListener listener) {
        mStudentsList = (List<Student>)items;
        mListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mStudentsList.get(position);
        holder.mName.setText(mStudentsList.get(position).getName());
        holder.mSchool.setText(mStudentsList.get(position).getSchool());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.showStudentInfoScreen(holder.mItem);
                }
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
        final TextView mSchool;
        Student mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.item_number);
            mSchool = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSchool.getText() + "'";
        }
    }
}
