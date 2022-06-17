package com.example.ajedrez.view.lessons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ajedrez.model.Assistance
import com.example.ajedrez.R

class LessonInfoAdapter internal constructor(
    private var mStudents: List<Assistance>
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

    inner class LessonInfoViewHolder internal constructor(mView: View) : RecyclerView.ViewHolder(mView) {

        val name: TextView = mView.findViewById(R.id.studentNameTextView)

        override fun toString(): String {
            return super.toString() + " "
        }
    }
}