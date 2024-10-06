package me.xiaoying.serverbuild.command.serverbuild.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileConfig;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "status", length = 0)
public class SBStatusCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileConfig.OVERALL_SITUATION_MESSAGE_HELP)
                .prefix(FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX)
                .date(FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileConfig.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION)
                    .prefix(FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX)
                    .date(FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                    .color()
                    .toString());
            return;
        }

        sender.sendMessage(new VariableFactory("").color().toString());
        sender.sendMessage(new VariableFactory("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>").color().toString());
        sender.sendMessage(new VariableFactory("&b|&6功能状态:").color().toString());
        SBPlugin.getModuleManager().getModules().forEach(module -> {

            if (!module.ready()) {
                sender.sendMessage(new VariableFactory("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&c已关闭").color().toString());
                return;
            }
            sender.sendMessage(new VariableFactory("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&e已开启").color().toString());
        });
        sender.sendMessage(new VariableFactory("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>").color().toString());
        sender.sendMessage(new VariableFactory("").color().toString());
    }
}