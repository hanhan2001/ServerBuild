package me.xiaoying.sb.command.notbuildcommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.NotBuildConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handler;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令 NotBuild reload
 */
@Command(values = "reload", length = 0)
public class NBReload extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.hasPermission("sb.nb.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(NotBuildConstant.MESSAGE_NOPERMISSION)
                            .prefix(NotBuildConstant.MESSAGE_PREFIX)
                            .date(NotBuildConstant.SET_VARIABLE_DATEFORMAT)
                            .color()
                            .getString());
            return false;
        }
        Handler.reloadHandle("NotBuild");
        sender.sendMessage(new VariableFactory(NotBuildConstant.MESSAGE_RELOAD)
                        .prefix(NotBuildConstant.MESSAGE_PREFIX)
                        .date(NotBuildConstant.SET_VARIABLE_DATEFORMAT)
                        .color()
                        .getString());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}