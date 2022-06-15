package com.example.ajedrez.network;

public interface StudentNotifications {
    void attachStudentObserver(Observer o);
    void detachStudentObserver(Observer o);
    void notifyUpdateStudentObservers();
}
