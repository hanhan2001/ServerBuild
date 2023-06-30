package me.xiaoying.sb.command.welcomemessagecommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.WelcomeMessageConstant;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.CommandSender;

@Command(values = "reload", length = 0)
public class WMReload extends SubCommand {
    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.hasPermission("sb.wm.admin") && !sender.isOp()) {
            sender.sendMessage(ColorUtil.translate(WelcomeMessageConstant.MESSAGE_PREFIX + WelcomeMessageConstant.MESSAGE_NOPERMISSION));
            return false;
        }

        Handler.reloadHandle("WelcomeMessage");
        return false;
    }
}