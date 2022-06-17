package com.example.ajedrez.model

import java.io.Serializable
import kotlin.collections.ArrayList

class Lesson: Serializable {

    var lessonId: String = ""
    var date: Long = 0
    var attendanceComplete: List<Assistance> = ArrayList()

    //DO NOT REMOVE
    constructor() {}

    constructor(id: String, date: Long, assistance: List<Assistance>) {
        this.lessonId = id
        this.date = date
        this.attendanceComplete =  assistance
    }

    val numberOfAssists: Int
        get() {
            var assists = 0
            for (student in attendanceComplete) {
                if (student.assisted == null) continue
                if (student.assisted!!) assists += 1
            }
            return assists
        }

    val attendance: List<Assistance>
        get() {
            val filteredList: MutableList<Assistance> = ArrayList()
            for (student in attendanceComplete) {
                if (student.assisted == null) continue
                if (student.assisted == true) {
                    filteredList.add(student)
                }
            }
            return filteredList
        }
}