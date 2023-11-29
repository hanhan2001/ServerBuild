package me.xiaoying.serverbuild.task;

public interface SubTask {
    int getId();
    String getName();

    void run();
    void stop();
}