package me.xiaoying.sb.command.messageannouncercommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.MessageAnnouncerConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handler;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令 MessageAnnouncer Reload
 */
@Command(values = "reload", length = 0)
public class MAReload extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.ma.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(MessageAnnouncerConstant.MESSAGE_NOPERMISSION)
                            .prefix(MessageAnnouncerConstant.MESSAGE_PREFIX)
                            .date(MessageAnnouncerConstant.SET_VARIABLE_DATEFORMAT)
                            .color()
                            .getString());
            return false;
        }

        Handler.reloadHandle("MessageAnnouncer");
        sender.sendMessage(new VariableFactory(MessageAnnouncerConstant.MESSAGE_RELOAD)
                        .prefix(MessageAnnouncerConstant.MESSAGE_PREFIX)
                        .date(MessageAnnouncerConstant.SET_VARIABLE_DATEFORMAT)
                        .color()
                        .getString());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}