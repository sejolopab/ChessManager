package com.example.ajedrez.view.assistance;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ajedrez.model.Assistance;
import com.example.ajedrez.R;
import com.example.ajedrez.utils.Utils;

import java.util.List;

public class AssistanceListAdapter extends RecyclerView.Adapter<AssistanceListAdapter.AssistanceViewHolder> {

    private List<Assistance> mAssistance;
    private final StudentsAssistanceListener mListener;

    public AssistanceListAdapter(List<Assistance> assistanceList, StudentsAssistanceListener listener) {
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
        String dateString = Utils.Companion.getStringTimeStamp(studentAssistance.getStudent().getLastAttendance());
        holder.mStudentSchool.setText(dateString);
        holder.mView.setBackgroundResource(R.drawable.assistance_swipe_item);
        holder.mStudentName.setTextColor(Color.BLACK);
        holder.mStudentSchool.setTextColor(Color.BLACK);

        if (studentAssistance.getAssisted() == null) {
            holder.mView.setBackgroundResource(R.drawable.assistance_swipe_item);
            holder.mStudentName.setTextColor(Color.BLACK);
            holder.mStudentSchool.setTextColor(Color.BLACK);
        } else if (studentAssistance.getAssisted()) {
            holder.mView.setBackgroundResource(R.color.green);
            holder.mStudentName.setTextColor(Color.WHITE);
            holder.mStudentSchool.setTextColor(Color.WHITE);
        } else {
            holder.mView.setBackgroundResource(R.color.lightRed);
            holder.mStudentName.setTextColor(Color.WHITE);
            holder.mStudentSchool.setTextColor(Color.WHITE);
        }

        holder.mView.setOnClickListener(v -> mListener.onAssistanceItemClick());
    }

    @Override
    public int getItemCount() {
        return mAssistance.size();
    }

    public void setAssistanceList(List<Assistance> studentsList) {
        mAssistance = studentsList;
    }

    public static class AssistanceViewHolder extends RecyclerView.ViewHolder {
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
