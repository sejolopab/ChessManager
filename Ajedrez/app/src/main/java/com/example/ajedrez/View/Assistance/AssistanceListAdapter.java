package com.example.ajedrez.View.Assistance;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.R;
import com.example.ajedrez.View.Assistance.StudentsAssistListFragment.StudentsAssistanceListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link StudentsAssistanceListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AssistanceListAdapter extends RecyclerView.Adapter<AssistanceListAdapter.AssistanceViewHolder> {

    private List<Assistance> mAssistance;
    private final StudentsAssistanceListener mListener;

    public AssistanceListAdapter(List<Assistance> assistanceList, StudentsAssistanceListener listener) {
        mAssistance = assistanceList;
        mListener = listener;
    }

    @Override
    public AssistanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_assistance_item, parent, false);
        return new AssistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AssistanceViewHolder holder, int position) {
        Assistance studentAssistance = mAssistance.get(position);
        holder.mItem = studentAssistance;
        holder.mStudentName.setText(studentAssistance.getStudent().getName());
        holder.mStudentSchool.setText(studentAssistance.getStudent().getSchool());

        if (studentAssistance.assisted == null) {
        } else if (studentAssistance.assisted) {
            holder.mView.setBackgroundResource(R.color.colorDarkGreen);
            holder.mStudentName.setTextColor(Color.WHITE);
            holder.mStudentSchool.setTextColor(Color.WHITE);
        } else {
            holder.mView.setBackgroundResource(R.color.colorDarkRed);
            holder.mStudentName.setTextColor(Color.WHITE);
            holder.mStudentSchool.setTextColor(Color.WHITE);
        }

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mAssistance.size();
    }

    public void setAssistanceList(List<Assistance> studentsList) {
        mAssistance = studentsList;
    }

    public class AssistanceViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mStudentName;
        final TextView mStudentSchool;
        Assistance mItem;

        public AssistanceViewHolder(View view) {
            super(view);
            mView = view;
            mStudentName = view.findViewById(R.id.name);
            mStudentSchool = view.findViewById(R.id.school);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mStudentSchool.getText() + "'";
        }
    }
}
