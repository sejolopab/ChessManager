package com.example.ajedrez.View.Lessons

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private fun loadAssistance() {
        val studentsQuery: Query = FirebaseDatabase.getInstance().reference.child("assistance")
        studentsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lessonsList: MutableList<Lesson> = ArrayList()
                for (assistanceList in dataSnapshot.children) {
                    val date = assistanceList.key
                    val assistants: MutableList<Assistance> = ArrayList()
                    for (student in assistanceList.children) {
                        val value = student.getValue(Assistance::class.java)!!
                        assistants.add(value)
                    }
                    lessonsList.add(Lesson(date!!, assistants))
                }
                //adapter!!.setLessonsList(lessonsList)
                //adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    interface EditLessonListener {
    }

    companion object {
        @JvmStatic
        fun newInstance(): LessonsListFragment {
            return LessonsListFragment()
        }
    }
}