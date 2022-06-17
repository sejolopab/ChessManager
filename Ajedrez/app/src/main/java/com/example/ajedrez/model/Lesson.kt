package com.example.ajedrez.model

import java.io.Serializable
import kotlin.collections.ArrayList

class Lesson: Serializable {

    var date: Long = 0
    var assistance: List<Assistance> = ArrayList()

    //DO NOT REMOVE
    constructor() {}

    constructor(date: Long, assistance: List<Assistance>) {
        this.date = date
        this.assistance =  assistance
    }

    val numberOfAssists: Int
        get() {
            var assists = 0
            for (student in assistance) {
                if (student.assisted == null) continue
                if (student.assisted!!) assists += 1
            }
            return assists
        }

    val students: List<Assistance>
        get() {
            val filteredList: MutableList<Assistance> = ArrayList()
            for (student in assistance) {
                if (student.assisted == null) continue
                if (student.assisted == true) {
                    filteredList.add(student)
                }
            }
            return filteredList
        }
}