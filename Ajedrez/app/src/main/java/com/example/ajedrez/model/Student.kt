package com.example.ajedrez.model

import java.io.Serializable

class Student : Serializable {
    var id: String? = null
    var name: String? = null
    var school: String? = null
    var phoneNumber: String? = null
    var birthDay: String? = null
    var startingDate: String? = null
    var lastClass: String? = null
    var lessons: List<Lesson>? = null
    var active: Boolean = true
}