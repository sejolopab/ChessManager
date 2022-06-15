package com.example.ajedrez.Network

interface LessonsNotifications {
    fun attachLessonsObserver(o: Observer?)
    fun detachLessonsObserver(o: Observer?)
    fun notifyUpdateLessonsObservers()
}