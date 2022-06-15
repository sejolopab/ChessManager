package com.example.ajedrez.View.Subjects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajedrez.Model.Subject;
import com.example.ajedrez.R;
import com.example.ajedrez.View.Subjects.SubjectsListFragment.SubjectsListener;

import java.util.List;

public class SubjectsListAdapter extends RecyclerView.Adapter<SubjectsListAdapter.ViewHolder> {

    private List<Subject> mValues;
    private final SubjectsListener mListener;

    SubjectsListAdapter(List<Subject> items, SubjectsListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_subjects_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).getTitle());
        holder.mContentView.setText("");
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.showSubjectReader(mValues.get(position).getFileName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    void setSubjectsList(List<Subject> subjectList) {
        mValues = subjectList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mName;
        final TextView mContentView;
        Subject mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
