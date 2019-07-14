package com.example.ajedrez.View.Assistance;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.R;
import com.example.ajedrez.View.Assistance.StudentsAssistListFragment.StudentsAssistanceListener;

import java.util.List;

public class AssistanceListAdapter extends RecyclerView.Adapter<AssistanceListAdapter.AssistanceViewHolder> {

    private List<Assistance> mAssistance;
    private final StudentsAssistanceListener mListener;

    AssistanceListAdapter(List<Assistance> assistanceList, StudentsAssistanceListener listener) {
        mAssistance = assistanceList;
        mListener = listener;
    }

    @NonNull
    @Override
    public AssistanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_assistance_item, parent, false);
        return new AssistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AssistanceViewHolder holder, int position) {
        Assistance studentAssistance = mAssistance.get(position);
        holder.mItem = studentAssistance;
        holder.mStudentName.setText(studentAssistance.getStudent().getName());
        holder.mStudentSchool.setText(studentAssistance.getStudent().getLastClass());
        holder.mView.setBackgroundResource(R.drawable.assistance_swipe_item);
        holder.mStudentName.setTextColor(Color.BLACK);
        holder.mStudentSchool.setTextColor(Color.BLACK);

        if (studentAssistance.assisted == null) {
            holder.mView.setBackgroundResource(R.drawable.assistance_swipe_item);
            holder.mStudentName.setTextColor(Color.BLACK);
            holder.mStudentSchool.setTextColor(Color.BLACK);
        } else if (studentAssistance.assisted) {
            holder.mView.setBackgroundResource(R.color.colorGreen);
            holder.mStudentName.setTextColor(Color.WHITE);
            holder.mStudentSchool.setTextColor(Color.WHITE);
        } else {
            holder.mView.setBackgroundResource(R.color.colorRed);
            holder.mStudentName.setTextColor(Color.WHITE);
            holder.mStudentSchool.setTextColor(Color.WHITE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAssistance.size();
    }

    void setAssistanceList(List<Assistance> studentsList) {
        mAssistance = studentsList;
    }

    public class AssistanceViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mStudentName;
        final TextView mStudentSchool;
        Assistance mItem;

        AssistanceViewHolder(View view) {
            super(view);
            mView = view;
            mStudentName = view.findViewById(R.id.name);
            mStudentSchool = view.findViewById(R.id.school);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mStudentSchool.getText() + "'";
        }

        public Assistance getItem() {
            return mItem;
        }
    }
}
