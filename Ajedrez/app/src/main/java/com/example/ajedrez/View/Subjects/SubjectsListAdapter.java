package com.example.ajedrez.View.Subjects;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajedrez.Model.Subject;
import com.example.ajedrez.R;
import com.example.ajedrez.View.Subjects.SubjectsListFragment.SubjectsListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Subject} and makes a call to the
 * specified {@link SubjectsListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SubjectsListAdapter extends RecyclerView.Adapter<SubjectsListAdapter.ViewHolder> {

    private List<Subject> mValues;
    private final SubjectsListener mListener;

    SubjectsListAdapter(List<Subject> items, SubjectsListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_subjects_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).getTitle());
        holder.mContentView.setText("");

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

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
