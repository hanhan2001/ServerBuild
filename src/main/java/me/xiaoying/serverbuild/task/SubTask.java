package me.xiaoying.serverbuild.task;

public interface SubTask {
    int getId();
    String getName();
    boolean isRunning();
    void setRunning(boolean running);

    void run();
    void stop();
}