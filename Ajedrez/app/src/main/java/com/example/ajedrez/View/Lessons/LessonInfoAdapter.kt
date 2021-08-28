package com.example.ajedrez.View.Lessons

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ajedrez.Model.Assistance
import com.example.ajedrez.R

class LessonInfoAdapter internal constructor(private var mStudents: List<Assistance>,
                                             private val mListener: LessonInfoFragment.LessonInfoListener?
) : RecyclerView.Adapter<LessonInfoAdapter.LessonInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_lesson_info_item, parent, false)
        return LessonInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonInfoViewHolder, position: Int) {
        holder.name.text = mStudents[position].student?.name ?: ""
    }

    override fun getItemCount(): Int {
        return mStudents.size
    }

    fun setStudentsList(studentList: List<Assistance>) {
        mStudents = studentList
    }

    inner class LessonInfoViewHolder internal constructor(val mView: View) : RecyclerView.ViewHolder(mView) {

        val name: TextView = mView.findViewById(R.id.studentNameTextView)

        override fun toString(): String {
            return super.toString() + " "
        }
    }
}