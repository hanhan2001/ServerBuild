package me.xiaoying.sb.listener;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.exception.ListenerException;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.utils.ExceptionUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListenerService {
    Map<Handle, List<Listener>> listeners = new HashMap<>();

    public void registerListener(Handle handle, Listener listener) {
        List<Listener> list;
        if (this.listeners.get(handle) != null)
            list = this.listeners.get(handle);
        else
            list = new ArrayList<>();

        if (list.contains(listener))
            return;

        list.add(listener);
        this.listeners.put(handle, list);
    }

    public void unregisterListener(Handle handle) {
        if (!this.listeners.containsKey(handle))
            return;

        // 关闭监听事件
        for (Listener listener : this.listeners.get(handle)) {
            for (RegisteredListener registeredListener : HandlerList.getRegisteredListeners(ServerBuild.getInstance())) {
                if (registeredListener.getListener() != listener)
                    continue;

                HandlerList.unregisterAll(listener);
            }
        }

        this.listeners.remove(handle);
    }

    public void runListener(Handle handle, Listener listener) {
        if (this.listeners.get(handle) == null)
            ExceptionUtil.throwException(new ListenerException("This handle(" + handle.getClass().getName() + ")'s listener is null"));

        if (!this.listeners.get(handle).contains(listener))
            ExceptionUtil.throwException(new ListenerException("Can't found " + listener.getClass().getName() + " listener in this handle(" + handle.getClass().getName() + ")"));

        ServerUtil.registerEvent(listener);
    }

    public void runListeners() {
        for (List<Listener> value : this.listeners.values())
            value.forEach(ServerUtil::registerEvent);
    }

    public void runListeners(Handle handle) {
        this.listeners.get(handle).forEach(ServerUtil::registerEvent);
    }

    public void stopListener(Handle handle, Listener listener) {
        if (this.listeners.get(handle) == null)
            ExceptionUtil.throwException(new ListenerException("This handle(" + handle.getClass().getName() + ")'s listener is null"));

        if (!this.listeners.get(handle).contains(listener))
            ExceptionUtil.throwException(new ListenerException("Can't found " + listener.getClass().getName() + " listener in this handle(" + handle.getClass().getName() + ")"));

        HandlerList.unregisterAll(listener);
    }

    public void stopListeners() {
        for (List<Listener> value : this.listeners.values())
            value.forEach(HandlerList::unregisterAll);
    }

    public List<Listener> getListeners(Handle handle) {
        return this.listeners.get(handle);
    }
}