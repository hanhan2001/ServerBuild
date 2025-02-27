package me.xiaoying.serverbuild.pluginmanager;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.utils.YamlUtil;
import me.xiaoying.serverbuild.utils.ZipUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class PaperPluginManager implements PluginManager {
    @Override
    public PluginResponse loadPlugin(String pluginName) {
        for (File file : Objects.requireNonNull(SBPlugin.getInstance().getDataFolder().getParentFile().listFiles())) {
            if (file.isDirectory())
                continue;

            if (!file.getName().endsWith(".rar") && !file.getName().endsWith(".zip") && !file.getName().equalsIgnoreCase(".7z") && !file.getName().endsWith(".jar"))
                continue;

            String content = ZipUtil.getFile(file.getAbsolutePath(), "plugin.yml");
            if (content == null || content.isEmpty())
                continue;

            Object object;
            if ((object = YamlUtil.getValueByKey(content, "name")) == null)
                continue;

            String name = object.toString();

            if (!name.equalsIgnoreCase(pluginName))
                continue;

            try {
                SBPlugin.getInstance().getPluginLoader().loadPlugin(file);
                return new PluginResponse(true, null);
            } catch (InvalidPluginException e) {
                System.out.println(new RuntimeException(e).getCause().fillInStackTrace().toString());
                return new PluginResponse(false, "");
//                throw new RuntimeException(e);
            }
        }
        return new PluginResponse(false, "Not found same name plugin.");
    }

    @Override
    public PluginResponse unloadPlugin(String pluginName) {
        PluginLoader pluginLoader = SBPlugin.getInstance().getPluginLoader();
        try {
            Class<? extends PluginLoader> pluginLoaderClass = pluginLoader.getClass();
            Field loadersField = pluginLoaderClass.getDeclaredField("loaders");
            loadersField.setAccessible(true);
            List<PluginLoader> loaders =  (List<PluginLoader>) loadersField.get(pluginLoader);

            for (PluginLoader loader : loaders) {
                Class<? extends PluginLoader> loaderClass = loader.getClass();
                Field pluginDescriptionField = loaderClass.getDeclaredField("description");
                pluginDescriptionField.setAccessible(true);
                PluginDescriptionFile pluginDescriptionFile = (PluginDescriptionFile) pluginDescriptionField.get(loader);
                if (!pluginDescriptionFile.getName().equalsIgnoreCase(pluginName))
                    continue;

                Field javaPlugin = loaderClass.getDeclaredField("pluginInit");
                javaPlugin.setAccessible(true);
                JavaPlugin plugin = (JavaPlugin) javaPlugin.get(loader);
                SBPlugin.getInstance().getPluginLoader().disablePlugin(plugin);
                return new PluginResponse(true, null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new PluginResponse(false, "Not found same name plugin.");
    }

    @Override
    public PluginResponse registerCommand(String command, CommandExecutor executor, Plugin plugin) {
        command = command.toLowerCase(Locale.ENGLISH);

        try {
            Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer().getPluginManager());
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, Command> commands = (Map<String, Command>) knownCommandsField.get(commandMap);
            for (Map.Entry<String, Command> stringCommandEntry : commands.entrySet()) {
                if (!(stringCommandEntry.getValue() instanceof PluginCommand))
                    continue;

                PluginCommand cmd = (PluginCommand) stringCommandEntry.getValue();
                if (cmd.getPlugin() == plugin && cmd.getName().equalsIgnoreCase(command))
                    return new PluginResponse(false, "Exists same command.");
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
        Objects.requireNonNull(plugin.getServer().getPluginCommand(command)).setExecutor(executor);
        return new PluginResponse(true, null);
    }

    @Override
    public PluginResponse unregisterCommand(String command, Plugin plugin) {
        command = command.toLowerCase(Locale.ENGLISH);

        try {
            Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer().getPluginManager());
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, Command> commands = (Map<String, Command>) knownCommandsField.get(commandMap);

            Iterator<String> iterator = commands.keySet().iterator();
            while (iterator.hasNext()) {
                String s = iterator.next();
                Command c = commands.get(s);
                PluginCommand pluginCommand;
                if (!(c instanceof PluginCommand) || (pluginCommand = (PluginCommand) commands.get(s)).getPlugin() != plugin || !c.getName().equalsIgnoreCase(command))
                    continue;

                pluginCommand.unregister(commandMap);
                iterator.remove();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new PluginResponse(true, null);
    }

    @Override
    public PluginResponse registerListener(Listener listener, Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        return new PluginResponse(true, null);
    }

    @Override
    public PluginResponse unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
        return new PluginResponse(true, null);
    }
}