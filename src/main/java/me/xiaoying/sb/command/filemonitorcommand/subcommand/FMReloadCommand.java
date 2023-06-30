package me.xiaoying.sb.command.filemonitorcommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.FileMonitorConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handler;
import org.bukkit.command.CommandSender;

@Command(values = "reload", length = 0)
public class FMReloadCommand extends SubCommand {
    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.fm.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileMonitorConstant.MESSAGE_NOPERMISSION)
                    .prefix(FileMonitorConstant.MESSAGE_PREFIX)
                    .date(FileMonitorConstant.SET_VARIABLE_DATEFORMAT)
                    .getString());
            return false;
        }

        Handler.getHandle("FileMonitor").reload();
        sender.sendMessage(new VariableFactory(FileMonitorConstant.MESSAGE_RELOAD)
                .prefix(FileMonitorConstant.MESSAGE_PREFIX)
                .date(FileMonitorConstant.SET_VARIABLE_DATEFORMAT)
                .getString());
        return false;
    }
}