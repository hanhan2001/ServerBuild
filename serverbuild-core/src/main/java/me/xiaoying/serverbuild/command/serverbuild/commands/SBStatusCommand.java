package me.xiaoying.serverbuild.command.serverbuild.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.common.ConfigCommon;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "status", length = 0, description = "查看所有 Module 状态")
public class SBStatusCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(ConfigCommon.OVERALL_SITUATION_MESSAGE_HELP)
                .prefix(ConfigCommon.OVERALL_SITUATION_VARIABLE_PREFIX)
                .date(ConfigCommon.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(ConfigCommon.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION)
                    .prefix(ConfigCommon.OVERALL_SITUATION_VARIABLE_PREFIX)
                    .date(ConfigCommon.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                    .color()
                    .toString());
            return;
        }

        sender.sendMessage(new VariableFactory("").color().toString());
        sender.sendMessage(new VariableFactory("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>").color().toString());
        sender.sendMessage(new VariableFactory("&b|&6功能状态:").color().toString());
        SBPlugin.getModuleManager().getModules().forEach(module -> {
            if (!module.isEnabled()) {
                sender.sendMessage(new VariableFactory("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&c已关闭").color().toString());
                return;
            }
            sender.sendMessage(new VariableFactory("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&e已开启").color().toString());
        });
        sender.sendMessage(new VariableFactory("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>").color().toString());
        sender.sendMessage(new VariableFactory("").color().toString());
    }
}