package me.xiaoying.sb.task;

public interface TaskHandle {
    void run();
    void stop();

    Integer getTask();
}