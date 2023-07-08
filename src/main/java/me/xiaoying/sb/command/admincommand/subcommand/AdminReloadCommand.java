package me.xiaoying.sb.command.admincommand.subcommand;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class AdminReloadCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin")) {
            sender.sendMessage(ColorUtil.translate(ConfigConstant.OVERALL_MESSAGE_PREFIX + ConfigConstant.OVERALL_MESSAGE_NOPERMISSION));
            return false;
        }

        ServerBuild.clear();
        Handler.reloadHandles();
        ServerBuild.initialize();
        sender.sendMessage(ColorUtil.translate(ConfigConstant.OVERALL_MESSAGE_PREFIX + ConfigConstant.OVERALL_MESSAGE_RELOAD));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}