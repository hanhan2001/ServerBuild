package me.xiaoying.sb.handle;

import java.util.HashMap;
import java.util.Map;

public class Handler {
    private static final Map<String, Handle> handles = new HashMap<>();

    public static void registerHandle(String name, Handle handle) {
        handles.put(name, handle);
    }

    public static void loadHandles() {
        for (Handle handle : handles.values()) {
            if (handle.enable())
                handle.onEnable();
            else
                handle.onDisable();
        }
    }

    public static Handle getHandle(String name) {
        return handles.get(name);
    }

    public static void unregisterHandle(String name) {
        handles.get(name).onDisable();
        handles.remove(name);
    }

    public static void reloadHandles() {
        for (Handle value : handles.values()) {
            value.reload();
        }
    }

    public static void reloadHandle(String name) {
        handles.get(name).reload();
    }
}