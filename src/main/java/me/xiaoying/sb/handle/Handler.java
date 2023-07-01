package me.xiaoying.sb.handle;

import java.util.HashMap;
import java.util.Map;

public class Handler {
    private static final Map<String, Handle> handles = new HashMap<>();

    public static void registerHandle(String name, Handle handle) {
        handles.put(name.toUpperCase(), handle);
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
        return handles.get(name.toUpperCase());
    }

    public static void unregisterHandle(String name) {
        handles.get(name.toUpperCase()).onDisable();
        handles.remove(name.toUpperCase());
    }

    public static void reloadHandles() {
        handles.values().forEach(Handle::reload);
    }

    public static void reloadHandle(String name) {
        handles.get(name.toUpperCase()).reload();
    }

    public static void stopHandles() {
        handles.values().forEach(Handle::stop);
    }

    public static void stopHandle(String name) {
        handles.get(name.toUpperCase()).stop();
    }
}