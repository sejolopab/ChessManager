package com.example.ajedrez.model

class Assistance {
    var assisted: Boolean? = null
    var student: Student? = null

    //DO NOT REMOVE
    constructor() {}

    constructor(student: Student?) {
        this.student = student
    }
}