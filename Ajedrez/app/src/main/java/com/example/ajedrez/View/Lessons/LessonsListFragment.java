package com.example.ajedrez.View.Lessons;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ajedrez.Data.DataManager;
import com.example.ajedrez.R;
import com.example.ajedrez.View.Assistance.AssistanceListAdapter;
import com.example.ajedrez.View.MainActivity;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link LessonsListener}
 * interface.
 */
public class LessonsListFragment extends Fragment {

    private LessonsListener mListener;
    private RecyclerView recyclerView;
    private LessonsListAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LessonsListFragment() {
    }

    public void setListener (MainActivity activity) {
        this.mListener = activity;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LessonsListFragment newInstance() {
        return new LessonsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons_list, container, false);
        recyclerView = view.findViewById(R.id.lessonsList);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new LessonsListAdapter(DataManager.getInstance().getLessons(), mListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        //adapter.setOnClickListener(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface LessonsListener {
    }
}
