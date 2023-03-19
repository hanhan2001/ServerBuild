package me.xiaoying.sb.command.notbuildcommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.files.config.FileNotBuild;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.CommandSender;

/**
 * 命令 NotBuild reload
 */
@Command(values = "reload", length = 1)
public class NBReload extends SubCommand {
    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.nb.admin") && !sender.isOp()) {
            sender.sendMessage(ColorUtil.translate(FileNotBuild.MESSAGE_PREFIX + FileNotBuild.MESSAGE_NOPERMISSION));
            return false;
        }
        Handler.reloadHandle("NotBuild");
        sender.sendMessage(ColorUtil.translate(FileNotBuild.MESSAGE_PREFIX + FileNotBuild.MESSAGE_RELOAD));
        return false;
    }
}