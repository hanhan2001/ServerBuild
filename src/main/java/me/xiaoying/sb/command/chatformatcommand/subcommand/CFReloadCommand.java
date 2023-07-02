package me.xiaoying.sb.command.chatformatcommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.ChatFormatConstant;
import me.xiaoying.sb.constant.LoginTpConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.CommandSender;

@Command(values = "reload", length = 0)
public class CFReloadCommand extends SubCommand {
    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.hasPermission("sb.ars.admin") && !sender.isOp()) {
            sender.sendMessage(ColorUtil.translate(LoginTpConstant.MESSAGE_PREFIX + LoginTpConstant.MESSAGE_NOPERMISSION));
            return false;
        }

        Handle handler = Handler.getHandle("ChatFormat");
        handler.reload();

        sender.sendMessage(new VariableFactory(ChatFormatConstant.MESSAGE_RELOAD)
                .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                .color()
                .getString());
        return false;
    }
}