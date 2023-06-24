package me.xiaoying.sb.entity;

import me.xiaoying.sb.file.files.FileChatFormat;

import java.util.ArrayList;
import java.util.List;

public class ChatFormatEntity {
    String key;

    int priority;
    String permission;
    List<String> format = new ArrayList<>();

    public ChatFormatEntity(String key) {
        this.priority = FileChatFormat.chatFormat.getInt("Formats." + key + ".Priority");
        this.permission = FileChatFormat.chatFormat.getString("Formats." + key + ".Permission");
        this.format = getStringList("Formats." + key + ".Format");
    }

    public int getPriority() {
        return this.priority;
    }

    public String getPermission() {
        return this.permission;
    }

    public List<String> getFormat() {
        return this.format;
    }

    public String getKey() {
        return this.key;
    }

    private static List<String> getStringList(String path) {
        List<String> function;
        try {
            function = FileChatFormat.chatFormat.getStringList(path);
        } catch (Exception e) {
            function = new ArrayList<>();
            function.add(FileChatFormat.chatFormat.getString(path));
        }
        return function;
    }
}