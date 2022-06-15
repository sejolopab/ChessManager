package com.example.ajedrez.view.students
import com.example.ajedrez.model.Student

interface StudentsListener {
    fun showAddStudentScreen()
    fun showStudentInfoScreen(student: Student?)
}