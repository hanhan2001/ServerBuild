package me.xiaoying.sb.command.admincommand.subcommand;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.CommandSender;

@Command(values = "reload", length = 0)
public class AdminReloadCommand extends SubCommand {
    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin")) {
            sender.sendMessage(ColorUtil.translate(ConfigConstant.OVERALL_MESSAGE_PREFIX + ConfigConstant.OVERALL_MESSAGE_NOPERMISSION));
            return false;
        }

        Handler.reloadHandles();
        ServerBuild.clear();
        sender.sendMessage(ColorUtil.translate(ConfigConstant.OVERALL_MESSAGE_PREFIX + ConfigConstant.OVERALL_MESSAGE_RELOAD));
        return false;
    }
}