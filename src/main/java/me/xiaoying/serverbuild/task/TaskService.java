package me.xiaoying.serverbuild.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TaskService {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(200);




    public ScheduledExecutorService getExecutor() {
        return this.executor;
    }
}