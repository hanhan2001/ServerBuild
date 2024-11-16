package me.xiaoying.serverbuild.pluginmanager;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public interface PluginManager {
    /**
     * Load plugin by name which from plugin.yml
     *
     * @param pluginName Plugin's name in plugin.yml
     */
    PluginResponse loadPlugin(String pluginName);

    /**
     * Unload plugin
     *
     * @param pluginName Plugin's name
     */
    PluginResponse unloadPlugin(String pluginName);

    PluginResponse registerCommand(String command, CommandExecutor executor, Plugin plugin);

    PluginResponse unregisterCommand(String command, Plugin plugin);

    PluginResponse registerListener(Listener listener, Plugin plugin);

    PluginResponse unregisterListener(Listener listener);
}