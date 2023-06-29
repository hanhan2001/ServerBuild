package me.xiaoying.sb.task;

import me.xiaoying.sb.exception.NotFoundTaskHandleException;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线程处理
 */
public class TaskService {
    Map<Handle, List<TaskHandle>> tasks = new HashMap<>();

    /**
     * 获取所有线程处理
     *
     * @return Map
     */
    public Map<Handle, List<TaskHandle>> getTasks() {
        return this.tasks;
    }

    /**
     * 获取单个处理类的所有线程处理
     *
     * @param handle 处理
     * @return List
     */
    public List<TaskHandle> getTasks(Handle handle) {
        if (this.tasks.get(handle) == null)
            return null;
        else
            return this.tasks.get(handle);
    }

    /**
     * 注册线程处理
     *
     * @param handle 处理
     * @param task 线程处理
     */
    public void registerTask(Handle handle, TaskHandle task) {
        List<TaskHandle> list;
        if ((list = this.tasks.get(handle)) == null)
            list = new ArrayList<>();

        list.add(task);
        this.tasks.put(handle, list);
    }

    /**
     * 取消注册处理的所有线程处理
     *
     * @param handle 处理
     */
    public void unregisterTasks(Handle handle) {
        for (TaskHandle task : this.tasks.get(handle))
            task.stop();
        this.tasks.remove(handle);
    }

    /**
     * 取消注册处理的单个线程处理
     *
     * @param handle 处理
     * @param task 线程处理
     */
    public void unregisterTask(Handle handle, TaskHandle task) {
        List<TaskHandle> list;
        if ((list = this.tasks.get(handle)) == null)
            return;

        if (!list.contains(task))
            ExceptionUtil.throwException(new NotFoundTaskHandleException("Cannot find TaskHandle '" + task.getClass().getName() + "'"));

        task.stop();
        list.remove(task);
    }

    /**
     * 启动所有线程处理
     */
    public void runTasks() {
        for (Handle handle : this.tasks.keySet()) {
            for (TaskHandle taskHandle : this.tasks.get(handle))
                taskHandle.run();
        }
    }

    /**
     * 启动单个处理的所有线程处理
     *
     * @param handle 处理
     */
    public void runTasks(Handle handle) {
        List<TaskHandle> list;

        if ((list = this.tasks.get(handle)) == null)
            return;
        for (TaskHandle taskHandle : list)
            taskHandle.run();
    }

    /**
     * 启动单个处理的线程处理
     *
     * @param handle 处理
     * @param task 线程处理
     */
    public void runTask(Handle handle, TaskHandle task) {
        List<TaskHandle> list;
        if ((list = this.tasks.get(handle)) == null)
            return;

        if (!list.contains(task))
            ExceptionUtil.throwException(new NotFoundTaskHandleException("Cannot find TaskHandle '" + task.getClass().getName() + "'"));

        task.stop();
    }

    /**
     * 关闭所有线程处理
     */
    public void stopTasks() {
        for (Handle handle : this.tasks.keySet()) {
            for (TaskHandle taskHandle : this.tasks.get(handle))
                taskHandle.stop();
        }
    }

    /**
     * 关闭单个处理的所有线程处理
     *
     * @param handle 处理
     */
    public void stopTasks(Handle handle) {
        List<TaskHandle> list;

        if ((list = this.tasks.get(handle)) == null)
            return;

        for (TaskHandle taskHandle : list)
            taskHandle.stop();
    }

    /**
     * 关闭单个处理的线程处理
     *
     * @param handle 处理
     * @param task 线程处理
     */
    public void stopTask(Handle handle, TaskHandle task) {
        List<TaskHandle> list;
        if ((list = this.tasks.get(handle)) == null)
            return;

        if (!list.contains(task))
            ExceptionUtil.throwException(new NotFoundTaskHandleException("Cannot find TaskHandle '" + task.getClass().getName() + "'"));

        task.stop();
    }
}