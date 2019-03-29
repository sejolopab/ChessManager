package com.example.ajedrez.View.Lessons;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajedrez.Model.Lesson;
import com.example.ajedrez.R;
import com.example.ajedrez.View.Lessons.LessonsListFragment.LessonsListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Lesson} and makes a call to the
 * specified {@link LessonsListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder> {

    private final List<Lesson> mValues;
    private final LessonsListener mListener;

    public LessonsListAdapter(List<Lesson> items, LessonsListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_lessons_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Lesson lesson = mValues.get(position);
        holder.mItem = lesson;
        holder.mDate.setText(lesson.getDate());
        holder.mAttendance.setText(String.valueOf(lesson.getAssistance()));

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
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDate;
        public final TextView mAttendance;
        public Lesson mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDate = view.findViewById(R.id.item_number);
            mAttendance = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAttendance.getText() + "'";
        }
    }
}