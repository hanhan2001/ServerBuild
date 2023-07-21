package me.xiaoying.sb.command.clearentitycommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.ClearEntityConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class CEReloadCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.hasPermission("sb.ce.admin") && !sender.isOp()) {
            sender.sendMessage(ColorUtil.translate(new VariableFactory(ClearEntityConstant.MESSAGE_NOPERMISSION)
                            .prefix(ClearEntityConstant.MESSAGE_PREFIX)
                            .date(ClearEntityConstant.SET_VARIABLE_DATEFORMAT)
                            .color()
                            .getString()));
            return false;
        }

        Handle handler = Handler.getHandle("ClearEntity");
        handler.reload();

        sender.sendMessage(new VariableFactory(ClearEntityConstant.MESSAGE_RELOAD)
                .prefix(ClearEntityConstant.MESSAGE_PREFIX)
                .date(ClearEntityConstant.SET_VARIABLE_DATEFORMAT)
                .color()
                .getString());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}