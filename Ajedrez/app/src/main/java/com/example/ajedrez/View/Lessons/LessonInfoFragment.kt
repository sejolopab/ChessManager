package com.example.ajedrez.View.Lessons

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ajedrez.Model.Lesson

import com.example.ajedrez.R
import com.example.ajedrez.View.MainActivity

class LessonInfoFragment : Fragment() {
    private val lessonKey = "lesson"
    private var lesson: Lesson = Lesson("", ArrayList())
    private var mListener: LessonInfoListener? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: LessonInfoAdapter? = null
    fun setListener(activity: MainActivity?) {
        mListener = activity
    }
    /*private var mListener: LessonsListFragment.LessonsListener? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: LessonsListAdapter? = null
    private var lessonList: List<Lesson>? = null
    fun setListener(activity: MainActivity?) {
        mListener = activity
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lesson_info, container, false)
        recyclerView = view.findViewById(R.id.lessonAssistance)
        //lessonList = ArrayList()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LessonInfoAdapter(lesson.students, mListener)
        adapter?.setStudentsList(lesson.students)

        recyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = adapter
    }

    interface LessonInfoListener {
        fun showStudentInfo()
    }

    companion object {
        @JvmStatic
        fun newInstance(pLesson: Lesson) =
                LessonInfoFragment().apply {
                    lesson = pLesson
                }
    }
}
