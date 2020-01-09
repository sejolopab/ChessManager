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
}