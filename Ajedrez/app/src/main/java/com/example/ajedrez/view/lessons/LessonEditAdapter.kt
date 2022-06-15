package com.example.ajedrez.view.lessons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ajedrez.model.Assistance
import com.example.ajedrez.model.Student
import com.example.ajedrez.R

class LessonEditAdapter internal constructor(private var mStudents: List<Student>,
                                             private val mListener: LessonEditFragment.EditLessonListener?
) : RecyclerView.Adapter<LessonEditAdapter.LessonEditViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonEditViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_student_item, parent, false)
        return LessonEditViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonEditViewHolder, position: Int) {
        holder.name.text = "prueba"//mStudents[position].student?.name ?: ""
    }

    override fun getItemCount(): Int {
        return mStudents.size
    }

    fun setStudentsList(studentList: List<Assistance>) {
        //Jeank Feo
    }

    inner class LessonEditViewHolder internal constructor(val mView: View) : RecyclerView.ViewHolder(mView) {

        val name: TextView = mView.findViewById(R.id.studentNameTextView)

        override fun toString(): String {
            return super.toString() + " "
        }
    }
}