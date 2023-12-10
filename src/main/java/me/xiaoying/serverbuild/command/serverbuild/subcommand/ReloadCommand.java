package me.xiaoying.serverbuild.command.serverbuild.subcommand;

import me.xiaoying.serverbuild.ServerBuild;
import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SubCommand;
import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.factory.VariableFactory;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Command ServerBuildCommand ReloadCommand
 */
@Command(values = "reload", length = 0)
public class ReloadCommand implements SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (sender.hasPermission("sb.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(ConstantCommon.SYSTEM_OVERALL_MESSAGE_NOPERMISSION)
                            .prefix(ConstantCommon.SYSTEM_OVERALL_VRAIABLE_PREFIX)
                            .date(ConstantCommon.SYSTEM_OVERALL_VARIABLE_DATEFORMAT)
                            .color()
                            .toString());
            return;
        }

        ServerBuild.unInitialize();
        ServerBuild.initialize();
        ServerBuild.runFunction();

        sender.sendMessage(new VariableFactory(ConstantCommon.SYSTEM_OVERALL_MESSAGE_COMPLETE)
                        .prefix(ConstantCommon.SYSTEM_OVERALL_VRAIABLE_PREFIX)
                        .date(ConstantCommon.SYSTEM_OVERALL_VARIABLE_DATEFORMAT)
                        .color()
                        .toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}