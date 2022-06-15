package com.example.ajedrez.network;

public interface AssistanceNotifications {
    void attachAssistanceObserver(Observer o);
    void detachAssistanceObserver(Observer o);
    void notifyUpdateAssistanceObservers();
}
