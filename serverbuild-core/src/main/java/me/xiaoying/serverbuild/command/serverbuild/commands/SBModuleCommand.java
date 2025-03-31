package me.xiaoying.serverbuild.command.serverbuild.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.modulecommand.SBModuleInfoCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.modulecommand.SBModuleOffCommand;
import me.xiaoying.serverbuild.command.serverbuild.commands.modulecommand.SBModuleOpenCommand;
import me.xiaoying.serverbuild.common.ConfigCommon;
import me.xiaoying.serverbuild.factory.VariableFactory;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "module", length = 2, parameters = {"info/off/open", "Module"})
public class SBModuleCommand extends SCommand {
    public SBModuleCommand() {
        this.registerCommand(new SBModuleOpenCommand());
        this.registerCommand(new SBModuleOffCommand());
        this.registerCommand(new SBModuleInfoCommand());
    }

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
    public void performCommand(CommandSender sender, String[] strings) {
        this.parentCommand(sender, strings);
    }
}