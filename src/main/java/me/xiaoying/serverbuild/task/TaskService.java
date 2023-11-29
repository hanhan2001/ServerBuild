package me.xiaoying.serverbuild.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TaskService
 */
public class TaskService {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(200);
    List<SubTask> knownTask = new ArrayList<>();

    /**
     * 注册 Task
     *
     * @param task Task
     */
    public void registerTask(SubTask task) {
        if (this.knownTask.contains(task))
            return;

        this.knownTask.add(task);
    }

    /**
     * 取消注册 Task
     *
     * @param name Task name
     */
    public void unregisterTask(String name) {
        for (SubTask subTask : this.knownTask) {
            if (!subTask.getName().equalsIgnoreCase(name))
                continue;

            subTask.stop();
            this.knownTask.remove(subTask);
        }
    }

    /**
     * 取消注册 Task
     *
     * @param task task
     */
    public void unregisterTask(SubTask task) {
        if (!this.knownTask.contains(task))
            return;

        task.stop();
        this.knownTask.remove(task);
    }

    /**
     * 取消所有注册 Task
     */
    public void unregisterTasks() {
        for (SubTask subTask : this.knownTask)
            subTask.stop();
        this.knownTask.clear();
    }

    /**
     * 获取 Task
     *
     * @param name Task name
     * @return SubTask
     */
    public SubTask getTask(String name) {
        for (SubTask subTask : this.knownTask) {
            if (!subTask.getName().equalsIgnoreCase(name))
                continue;

            return subTask;
        }
        return null;
    }

    /**
     * 获取所有 Task
     *
     * @return ArrayList
     */
    public List<SubTask> getTasks() {
        return this.knownTask;
    }

    /**
     * 获取 scheduled
     *
     * @return ScheduleExecutorService
     */
    public ScheduledExecutorService getScheduledExecutor() {
        return this.executor;
    }
}