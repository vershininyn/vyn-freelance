package dev.projects.sspsoft.service.mq;

public interface MessageQueue {
    void send(String message);
}