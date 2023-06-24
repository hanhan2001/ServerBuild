package me.xiaoying.sb.command.logintpcommand;

import me.xiaoying.sb.command.RegisteredCommand;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.command.logintpcommand.subcommand.LTPReloadCommand;
import me.xiaoying.sb.command.logintpcommand.subcommand.LTPSetLocationCommand;
import me.xiaoying.sb.constant.LoginTpConstant;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * 命令 LoginTP
 */
public class LoginTPCommand implements TabExecutor {
    public static Map<String, List<RegisteredCommand>> registeredCommands = new HashMap<>();

    public LoginTPCommand() {
        this.registerCommand(new LTPReloadCommand());
        this.registerCommand(new LTPSetLocationCommand());
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
            LoginTpConstant.MESSAGE_HELP.forEach(string -> sender.sendMessage(ColorUtil.translate(string)));
            return false;
        }

        String cmd = args[0];
        if (!registeredCommands.containsKey(cmd)) {
            LoginTpConstant.MESSAGE_HELP.forEach(string -> sender.sendMessage(ColorUtil.translate(string)));
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
            LoginTpConstant.MESSAGE_HELP.forEach(string -> sender.sendMessage(ColorUtil.translate(string)));
            return false;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>(registeredCommands.keySet());
    }
}