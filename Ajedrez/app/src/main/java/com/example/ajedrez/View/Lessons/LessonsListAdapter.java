package com.example.ajedrez.View.Lessons;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajedrez.Model.Lesson;
import com.example.ajedrez.R;
import com.example.ajedrez.View.Lessons.LessonsListFragment.LessonsListener;

import java.util.List;

public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder> {

    private List<Lesson> mValues;
    private final LessonsListener mListener;

    LessonsListAdapter(List<Lesson> items, LessonsListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lessons_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Lesson lesson = mValues.get(position);
        holder.mItem = lesson;
        holder.mDate.setText(lesson.getDate());
        holder.mAttendance.setText(String.valueOf(lesson.getNumberOfAssists()));
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
        return mValues.size();
    }

    void setLessonsList(List<Lesson> lessonsList) {
        this.mValues = lessonsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mDate;
        final TextView mAttendance;
        Lesson mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mDate = view.findViewById(R.id.item_number);
            mAttendance = view.findViewById(R.id.content);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mAttendance.getText() + "'";
        }
    }
}
