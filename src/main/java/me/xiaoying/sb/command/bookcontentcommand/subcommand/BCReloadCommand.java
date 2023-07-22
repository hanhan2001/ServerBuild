package me.xiaoying.sb.command.bookcontentcommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.BookContentConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class BCReloadCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!ServerUtil.hasPermission(sender,  "sb.bc.admin", "sb.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(BookContentConstant.MESSAGE_NOPERMISSION)
                            .prefix(BookContentConstant.MESSAGE_PREFIX)
                            .date(BookContentConstant.SET_VARIABLE_DATEFORMAT)
                            .color()
                            .getString());
            return false;
        }

        Handler.reloadHandle("BookContent");

        sender.sendMessage(new VariableFactory(BookContentConstant.MESSAGE_RELOAD)
                        .prefix(BookContentConstant.MESSAGE_PREFIX)
                        .date(BookContentConstant.SET_VARIABLE_DATEFORMAT)
                        .color()
                        .getString());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}