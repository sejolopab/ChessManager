package com.example.ajedrez.Model

import java.io.Serializable

class Lesson(val date: String, val assistance: List<Assistance>): Serializable {

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