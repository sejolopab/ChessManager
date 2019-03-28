package com.example.ajedrez.View.Assistance;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ajedrez.Model.Assistance;
import com.example.ajedrez.Model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.View.Assistance.StudentsAssistListFragment.StudentsAssistanceListener;

import java.util.List;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link StudentsAssistanceListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AssistanceListAdapter extends RecyclerView.Adapter<AssistanceListAdapter.AssistanceViewHolder> {

    private final List<Assistance> mAssistance;
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
        holder.mIdView.setText(studentAssistance.getStudent().getName());
        holder.mContentView.setText(studentAssistance.getStudent().getSchool());

        if (studentAssistance.assisted == null) {
        } else if (studentAssistance.assisted) {
            holder.mView.setBackgroundResource(R.color.colorDarkGreen);
            holder.mIdView.setTextColor(Color.WHITE);
            holder.mContentView.setTextColor(Color.WHITE);
        } else {
            holder.mView.setBackgroundResource(R.color.colorDarkRed);
            holder.mIdView.setTextColor(Color.WHITE);
            holder.mContentView.setTextColor(Color.WHITE);
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

    public void setBackground() {

    }

    public class AssistanceViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Assistance mItem;

        public AssistanceViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.name);
            mContentView = (TextView) view.findViewById(R.id.school);
        }

        public void setAssisted(boolean assisted) {
            if (assisted) {
                mView.setBackgroundColor(GREEN);
            } else {
                mView.setBackgroundColor(RED);
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
