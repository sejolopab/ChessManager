package com.example.ajedrez.Model

class Lesson(val date: String, val assistance: List<Assistance>) {

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