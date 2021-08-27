package com.example.ajedrez.View.Lessons

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ajedrez.Model.Lesson

import com.example.ajedrez.R

class LessonInfoFragment : Fragment() {

    private val lessonKey = "lesson"
    private var lesson: Lesson? = null
    private var listener: LessonInfoListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lesson = it.getSerializable(lessonKey) as Lesson?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lesson_info, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LessonInfoListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface LessonInfoListener {
        fun showStudentInfo()
    }

    companion object {
        @JvmStatic
        fun newInstance(lesson: Lesson) =
                LessonInfoFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(lessonKey, lesson)
                    }
                }
    }
}
