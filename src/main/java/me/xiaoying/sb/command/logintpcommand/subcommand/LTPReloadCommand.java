package me.xiaoying.sb.command.logintpcommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.LoginTpConstant;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.CommandSender;

/**
 * 命令 LoginTP reload
 */
@Command(values = "reload", length = 1)
public class LTPReloadCommand extends SubCommand {
    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.hasPermission("sb.lt.admin") && !sender.isOp()) {
            sender.sendMessage(ColorUtil.translate(LoginTpConstant.MESSAGE_PREFIX + LoginTpConstant.MESSAGE_NOPERMISSION));
            return false;
        }
        Handler.reloadHandle("LoginTP");
        sender.sendMessage(ColorUtil.translate(LoginTpConstant.MESSAGE_PREFIX + LoginTpConstant.MESSAGE_RELOAD));
        return false;
    }
}