package com.example.ajedrez.view.lessons

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ajedrez.model.Lesson
import com.example.ajedrez.network.Network
import com.example.ajedrez.R
import com.example.ajedrez.view.BaseFragment
import java.util.*

class LessonsListFragment : BaseFragment(), Observer {

    //==============================================================================================
    // View objects
    //==============================================================================================

    private var mListener: LessonsListener? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: LessonsListAdapter? = null
    private var lessonList: List<Lesson>? = null

    //==============================================================================================
    // Lifecycle
    //==============================================================================================

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lessons_list, container, false)
        recyclerView = view.findViewById(R.id.lessonsList)
        lessonList = Network.getInstance().lessons
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = lessonList?.let { LessonsListAdapter(it, mListener) }
        recyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = adapter
        loadAssistance()
    }

    override fun onResume() {
        super.onResume()
        hideKeyboardFrom(context, view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as LessonsListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement MyInterface ")
        }
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    private fun loadAssistance() {
        lessonList?.let { adapter!!.setLessonsList(it) }
        adapter!!.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): LessonsListFragment {
            return LessonsListFragment()
        }
    }

    //==============================================================================================
    // LessonNotifications
    //==============================================================================================

    override fun update(p0: Observable?, p1: Any?) {
        lessonList = Network.getInstance().lessons
        loadAssistance()
    }
}