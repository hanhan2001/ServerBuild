package me.xiaoying.serverbuild.command.serverbuild.commands;

import me.xiaoying.serverbuild.ServerBuild;
import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileConfig;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class SBReloadCommand extends SCommand {
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
    public void performCommand(CommandSender sender, String[] strings) {
        if (!sender.hasPermission("sb.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileConfig.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION)
                            .prefix(FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX)
                            .date(FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                            .color()
                            .toString());
            return;
        }

        ServerBuild.unInitialize();
        ServerBuild.initialize();

        SBPlugin.getModuleManager().getModules().forEach(module -> {
            module.init();

            if (!module.ready()) {
                module.disable();
                return;
            }

            module.enable();
        });

        sender.sendMessage(new VariableFactory(FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD)
                        .prefix(FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX)
                        .date(FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                        .color()
                        .toString());
    }
}