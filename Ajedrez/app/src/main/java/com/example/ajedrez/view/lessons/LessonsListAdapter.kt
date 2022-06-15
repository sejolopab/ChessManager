package com.example.ajedrez.view.lessons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ajedrez.model.Lesson
import com.example.ajedrez.R

class LessonsListAdapter internal constructor(private var mLessonsList: List<Lesson>,
                                              private val mListener: LessonsListener?) : RecyclerView.Adapter<LessonsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_lessons_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = mLessonsList[position]
        holder.mItem = lesson
        holder.mDate.text = lesson.date
        holder.mAttendance.text = lesson.numberOfAssists.toString()
        holder.mView.setOnClickListener {
            mListener?.showLessonInfoScreen(lesson)
        }
    }

    override fun getItemCount(): Int {
        return mLessonsList.size
    }

    fun setLessonsList(lessonsList: List<Lesson>) {
        mLessonsList = lessonsList
    }

    inner class ViewHolder internal constructor(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mDate: TextView = mView.findViewById(R.id.item_number)
        val mAttendance: TextView = mView.findViewById(R.id.content)
        var mItem: Lesson? = null

        override fun toString(): String {
            return super.toString() + " '" + mAttendance.text + "'"
        }

    }

}