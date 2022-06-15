package com.example.ajedrez.View.Lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ajedrez.Model.Assistance
import com.example.ajedrez.Model.Lesson
import com.example.ajedrez.R
import com.example.ajedrez.View.BaseFragment
import com.google.firebase.database.*
import java.util.*

class LessonEditFragment  : BaseFragment() {
    private var mListener: EditLessonListener? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: LessonEditAdapter? = null
    private var studentsList: List<Lesson>? = null

    /*fun setListener(activity: MainActivity?) {
        mListener = activity
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lesson_edit, container, false)
        recyclerView = view.findViewById(R.id.studentsList)
        studentsList = ArrayList()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //adapter = studentsList?.let { LessonEditAdapter(it, mListener) }
       // recyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //recyclerView!!.adapter = adapter
        //loadAssistance()
    }

    override fun onResume() {
        super.onResume()
        hideKeyboardFrom(
            Objects.requireNonNull(context), Objects.requireNonNull(
                view
            )
        )
    }

    interface EditLessonListener {
    }

    companion object {
        fun newInstance(): LessonsListFragment {
            return LessonsListFragment()
        }
    }
}