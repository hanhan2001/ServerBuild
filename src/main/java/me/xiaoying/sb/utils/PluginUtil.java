package me.xiaoying.sb.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class PluginUtil {
    /**
     * 移除插件命令
     *
     * @param command 主命令名称
     * @param plugin 插件
     */
    public static void unregisterCommand(String command, Plugin plugin) {
        try {
            Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer().getPluginManager());
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, Command> commands = (Map) knownCommandsField.get(commandMap);
            Iterator it = commands.entrySet().iterator();
            while (it.hasNext()) {
                PluginCommand c;
                Map.Entry entry = (Map.Entry) it.next();
                if (!(entry.getValue() instanceof PluginCommand) ||
                        (c = (PluginCommand)entry.getValue()).getPlugin() != plugin ||
                        !((PluginCommand) entry.getValue()).getName().equalsIgnoreCase(command))
                    continue;
                c.unregister(commandMap);
                it.remove();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册插件指令
     *
     * @param command 命令
     * @param plugin 插件
     */
    public static void registerCommand(String command, Plugin plugin) {
        try {
            Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer().getPluginManager());
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, Command> commands = (Map) knownCommandsField.get(commandMap);
            for (Map.Entry<String, Command> stringCommandEntry : commands.entrySet()) {
                if (!(stringCommandEntry.getValue() instanceof PluginCommand))
                    continue;

                PluginCommand cmd = (PluginCommand) stringCommandEntry.getValue();
                if (cmd.getPlugin() == plugin && cmd.getName().equalsIgnoreCase(command))
                    return;
            }

            Class<PluginCommand> clazz = PluginCommand.class;
            Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            PluginCommand pluginCommand = (PluginCommand) constructor.newInstance(command, plugin);
            pluginCommand.register(commandMap);
            ((Map<String, Command>) knownCommandsField.get(commandMap)).put(command, pluginCommand);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}