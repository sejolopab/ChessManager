package com.example.ajedrez.Network;

public interface StudentNotifications {
    void attachStudentObserver(Observer o);
    void detachStudentObserver(Observer o);
    void notifyUpdateStudentObservers();
}
