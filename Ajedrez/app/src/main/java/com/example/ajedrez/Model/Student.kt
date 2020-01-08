package com.example.ajedrez.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Student : Serializable, Parcelable {
    var id: String? = null
    var name: String? = null
    var school: String? = null
    var phoneNumber: String? = null
    var birthDay: String? = null
    var startingDate: String? = null
    var lastClass: String? = null
    var lessons: List<Lesson>? = null

    constructor() {}
    protected constructor(`in`: Parcel) {
        id = `in`.readString()
        name = `in`.readString()
        school = `in`.readString()
        phoneNumber = `in`.readString()
        birthDay = `in`.readString()
        startingDate = `in`.readString()
        lastClass = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(school)
        dest.writeString(startingDate)
        dest.writeString(lastClass)
        dest.writeString(phoneNumber)
        dest.writeString(birthDay)
    }

    /*companion object {
        val CREATOR: Parcelable.Creator<Student?> = object : Parcelable.Creator<Student?> {
            override fun createFromParcel(`in`: Parcel): Student? {
                return Student(`in`)
            }

            override fun newArray(size: Int): Array<Student?> {
                return arrayOfNulls(size)
            }
        }
    }*/

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}