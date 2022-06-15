package com.example.ajedrez.View.Lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ajedrez.Model.Lesson

import com.example.ajedrez.R
import com.example.ajedrez.View.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class LessonInfoFragment : BaseFragment() {
    private val lessonKey = "lesson"
    private var lesson: Lesson = Lesson("", ArrayList())
    private var mListener: LessonInfoListener? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: LessonInfoAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lesson_info, container, false)
        recyclerView = view.findViewById(R.id.lessonAssistance)

        val editButton: FloatingActionButton = view.findViewById(R.id.editLessonFBTN)
        editButton.setOnClickListener { editButtonAction() }

        return view
    }

    fun editButtonAction() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LessonInfoAdapter(lesson.students, mListener)
        adapter?.setStudentsList(lesson.students)

        recyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        BaseFragment.hideKeyboardFrom(
            Objects.requireNonNull(context), Objects.requireNonNull(
                view
            )
        )
    }

    interface LessonInfoListener {
        fun showStudentInfo()
    }

    companion object {
        fun newInstance(pLesson: Lesson): LessonInfoFragment {
            val nFragment = LessonInfoFragment()
            nFragment.lesson = pLesson
            return nFragment
        }
    }
}
