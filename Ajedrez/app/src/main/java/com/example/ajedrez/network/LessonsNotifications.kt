package com.example.ajedrez.network

interface LessonsNotifications {
    fun attachLessonsObserver(o: Observer?)
    fun detachLessonsObserver(o: Observer?)
    fun notifyUpdateLessonsObservers()
}