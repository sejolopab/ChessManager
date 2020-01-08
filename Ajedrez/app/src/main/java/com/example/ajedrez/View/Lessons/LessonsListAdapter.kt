package com.example.ajedrez.View.Lessons

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ajedrez.Model.Lesson
import com.example.ajedrez.R
import com.example.ajedrez.View.Lessons.LessonsListFragment.LessonsListener

class LessonsListAdapter internal constructor(private var mValues: List<Lesson>,
                                              private val mListener: LessonsListener?) : RecyclerView.Adapter<LessonsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_lessons_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = mValues[position]
        holder.mItem = lesson
        holder.mDate.text = lesson.date
        holder.mAttendance.text = lesson.numberOfAssists.toString()
        holder.mView.setOnClickListener {
            mListener?.showLessonInfoScreen(lesson)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    fun setLessonsList(lessonsList: List<Lesson>) {
        mValues = lessonsList
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