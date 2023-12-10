package me.xiaoying.serverbuild.command.serverbuild;

import me.xiaoying.serverbuild.command.RegisteredCommand;
import me.xiaoying.serverbuild.command.SubCommand;
import me.xiaoying.serverbuild.command.serverbuild.subcommand.ReloadCommand;
import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

/**
 * Command ServerBuild
 */
public class ServerBuildCommand implements TabExecutor {
    private final Map<String, List<RegisteredCommand>> registeredCommands = new HashMap<>();

    public ServerBuildCommand() {
        this.registerCommand(new ReloadCommand());
    }

    private void registerCommand(SubCommand subCommand) {
        me.xiaoying.serverbuild.command.Command command = subCommand.getClass().getAnnotation(me.xiaoying.serverbuild.command.Command.class);

        if (command == null) {
            ServerUtil.sendMessage("&echecked some command(" + subCommand.getClass().getName() + ") don't use Command annotation, please check your code!", true);
            return;
        }

        for (String s : command.values()) {
            List<RegisteredCommand> list = new ArrayList<>();
            for (int i : command.length())
                list.add(new RegisteredCommand(i, subCommand));

            this.registeredCommands.put(s, list);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args == null || args.length == 0) {
            ConstantCommon.SYSTEM_OVERALL_MESSAGE_HELP.forEach(string -> sender.sendMessage(new VariableFactory(string)
                            .prefix(ConstantCommon.SYSTEM_OVERALL_VRAIABLE_PREFIX)
                            .date(ConstantCommon.SYSTEM_OVERALL_VARIABLE_DATEFORMAT)
                            .color()
                            .toString()));
            return false;
        }

        // 判断是否存在相应命令
        String head = args[0];
        if (!this.registeredCommands.containsKey(head)) {
            ConstantCommon.SYSTEM_OVERALL_MESSAGE_HELP.forEach(string -> sender.sendMessage(new VariableFactory(string)
                    .prefix(ConstantCommon.SYSTEM_OVERALL_VRAIABLE_PREFIX)
                    .date(ConstantCommon.SYSTEM_OVERALL_VARIABLE_DATEFORMAT)
                    .color()
                    .toString()));
            return false;
        }

        // 移除 head
        List<String> list = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
        args = list.toArray(new String[0]);

        boolean isDo = false;
        for (RegisteredCommand registeredCommand : this.registeredCommands.get(head)) {
            if (registeredCommand.getLength() != args.length)
                continue;

            registeredCommand.getSubCommand().performCommand(sender, args);
            isDo = true;
        }

        if (isDo)
            return true;

        // 未执行则发出帮助信息
        ConstantCommon.SYSTEM_OVERALL_MESSAGE_HELP.forEach(string -> sender.sendMessage(new VariableFactory(string)
                .prefix(ConstantCommon.SYSTEM_OVERALL_VRAIABLE_PREFIX)
                .date(ConstantCommon.SYSTEM_OVERALL_VARIABLE_DATEFORMAT)
                .color()
                .toString()));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
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