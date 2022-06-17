package com.example.ajedrez.view.lessons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ajedrez.model.Assistance
import com.example.ajedrez.R

class LessonEditAdapter internal constructor(private var assistanceList: List<Assistance>) :
    RecyclerView.Adapter<LessonEditAdapter.LessonEditViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonEditViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_assistance_item, parent, false)
        return LessonEditViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonEditViewHolder, position: Int) {
        holder.name.text = assistanceList[position].student?.name ?: "Error"
    }

    override fun getItemCount(): Int {
        return assistanceList.size
    }

    fun setStudentsList(studentList: List<Assistance>) {
        this.assistanceList = studentList
    }

    inner class LessonEditViewHolder internal constructor(mView: View) : RecyclerView.ViewHolder(mView) {

        val name: TextView = mView.findViewById(R.id.studentNameTextView)

        override fun toString(): String {
            return super.toString() + " "
        }
    }
}