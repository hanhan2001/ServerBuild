package me.xiaoying.serverbuild.function.functions.chatformat;

import me.xiaoying.serverbuild.ServerBuild;
import me.xiaoying.serverbuild.file.file.FileChatFormat;
import me.xiaoying.serverbuild.function.functions.ChatFormatFunction;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ChatEntity {
    String name;
    String permission;
    int priority = 0;
    List<String> format = new ArrayList<>();

    public ChatEntity(String formatName) {
        this.name = formatName;

        ChatFormatFunction function = (ChatFormatFunction) ServerBuild.getFunctionService().getFunction("ChatFormat");
        YamlConfiguration yamlConfiguration = function.getFiles().get(0).getYamlConfiguration();
        this.permission = yamlConfiguration.getString("Formats." + formatName + ".Permission");
        this.format = yamlConfiguration.getStringList("Formats." + formatName + ".Permission");
        this.permission = yamlConfiguration.getString("Formats." + formatName + ".Permission");
    }

    public ChatEntity(String name, String permission, int priority, List<String> format) {
        this.name = name;
        this.permission = permission;
        this.priority = priority;
        this.format = format;
    }

    public String getName() {
        return this.name;
    }

    public String getPermission() {
        return this.permission;
    }

    public int getPriority() {
        return this.priority;
    }

    public List<String> getFormat() {
        return this.format;
    }
}