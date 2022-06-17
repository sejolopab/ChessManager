package com.example.ajedrez.view.lessons

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ajedrez.model.Lesson

import com.example.ajedrez.R
import com.example.ajedrez.view.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LessonInfoFragment : BaseFragment() {

    //==============================================================================================
    // Properties
    //==============================================================================================

    private var recyclerView: RecyclerView? = null

    private var lesson: Lesson = Lesson()
    private var mListener: LessonInfoListener? = null
    private var adapter: LessonInfoAdapter? = null

    //==============================================================================================
    // Lifecycle
    //==============================================================================================

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lesson_info, container, false)
        recyclerView = view.findViewById(R.id.lessonAssistance)

        val editButton: FloatingActionButton = view.findViewById(R.id.editLessonFBTN)
        editButton.setOnClickListener {
            mListener?.showEditLesson(lesson);
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LessonInfoAdapter(lesson.students)
        adapter?.setStudentsList(lesson.students)

        recyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        hideKeyboardFrom(context, view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as LessonInfoListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement MyInterface ")
        }
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    companion object {
        fun newInstance(pLesson: Lesson): LessonInfoFragment {
            val nFragment = LessonInfoFragment()
            nFragment.lesson = pLesson
            return nFragment
        }
    }
}
