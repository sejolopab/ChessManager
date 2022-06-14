package com.example.ajedrez.Network;

public interface AssistanceNotifications {
    void attachAssistanceObserver(Observer o);
    void detachAssistanceObserver(Observer o);
    void notifyUpdateAssistanceObservers();
}
