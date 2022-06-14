package com.example.ajedrez.View.Students
import com.example.ajedrez.Model.Student

interface StudentsListener {
    fun showAddStudentScreen()
    fun showStudentInfoScreen(student: Student?)
}