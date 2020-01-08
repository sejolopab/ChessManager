package com.example.ajedrez.View.Lessons

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ajedrez.Model.Lesson

import com.example.ajedrez.R

private const val ARG_LESSON = "lesson"

class LessonInfoFragment : Fragment() {

    private var lesson: Lesson? = null
    private var listener: LessonInfoListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //lesson = it.getParcelable<Lesson>(ARG_LESSON)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson_info, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        listener?.showStudentInfo()
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LessonInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(lesson: Lesson) =
                LessonInfoFragment().apply {
                    arguments = Bundle().apply {
                        //putParcelable(ARG_LESSON, lesson)
                    }
                }
    }
}
