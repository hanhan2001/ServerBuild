package me.xiaoying.sb.command.homecommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.HomeConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handler;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class HReloadCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.home.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(HomeConstant.MESSAGE_NOPERMISSION)
                            .prefix(HomeConstant.MESSAGE_PREFIX)
                            .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                            .color()
                            .getString());
            return false;
        }

        Handler.reloadHandle("Home");
        sender.sendMessage(new VariableFactory(HomeConstant.MESSAGE_RELOAD)
                        .prefix(HomeConstant.MESSAGE_PREFIX)
                        .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                        .color()
                        .getString());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}