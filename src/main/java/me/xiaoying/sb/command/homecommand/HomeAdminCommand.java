package me.xiaoying.sb.command.homecommand;

import me.xiaoying.sb.command.RegisteredCommand;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.command.homecommand.subcommand.HReload;
import me.xiaoying.sb.constant.HomeConstant;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * 命令 Home
 */
public class HomeAdminCommand implements TabExecutor {
    private final Map<String, List<RegisteredCommand>> registeredCommands = new HashMap<>();

    public HomeAdminCommand() {
        this.registerCommand(new HReload());
    }

    private void registerCommand(SubCommand subCommand) {
        for (String value : subCommand.getClass().getAnnotation(me.xiaoying.sb.command.Command.class).values()) {
            List<RegisteredCommand> list = new ArrayList<>();
            for (int i : subCommand.getClass().getAnnotation(me.xiaoying.sb.command.Command.class).length()) {
                list.add(new RegisteredCommand(i, subCommand));
            }
            registeredCommands.put(value, list);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args == null || args.length == 0) {
            HomeConstant.MESSAGE_HELP.forEach(string -> sender.sendMessage(ColorUtil.translate(string)));
            return false;
        }

        String cmd = args[0];
        if (!registeredCommands.containsKey(cmd)) {
            HomeConstant.MESSAGE_HELP.forEach(string -> sender.sendMessage(ColorUtil.translate(string)));
            return false;
        }

        List<String> list = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
        args = list.toArray(new String[0]);

        boolean isDo = false;
        for (RegisteredCommand commandEntity : registeredCommands.get(cmd)) {
            if (commandEntity.getLength() != args.length)
                continue;

            commandEntity.getSubCommand().performCommand(sender, args);
            isDo = true;
        }

        if (!isDo) {
            HomeConstant.MESSAGE_HELP.forEach(string -> sender.sendMessage(ColorUtil.translate(string)));
            return false;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>(registeredCommands.keySet());
        if (strings.length == 1) {
            List<String> conditionList = new ArrayList<>();
            for (String s1 : list) {
                if (!s1.startsWith(strings[0]))
                    continue;
                conditionList.add(s1);
            }

            if (conditionList.size() == 0)
                return list;
            return conditionList;
        }

        List<RegisteredCommand> registeredCommand = registeredCommands.get(strings[0]);
        if (registeredCommand == null)
            return new ArrayList<>();

        for (RegisteredCommand registeredCommand1 : registeredCommand) {
            List<String> l;
            if ((l = registeredCommand1.getSubCommand().onTabComplete(commandSender, command, s, strings)) == null)
                return null;

            return l;
        }
        return new ArrayList<>();
    }
}