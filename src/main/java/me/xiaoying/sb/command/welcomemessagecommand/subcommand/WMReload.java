package me.xiaoying.sb.command.welcomemessagecommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.WelcomeMessageConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class WMReload extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.hasPermission("sb.wm.admin") && !sender.isOp()) {
            sender.sendMessage(ColorUtil.translate(WelcomeMessageConstant.MESSAGE_PREFIX + WelcomeMessageConstant.MESSAGE_NOPERMISSION));
            return false;
        }

        Handler.reloadHandle("WelcomeMessage");
        sender.sendMessage(new VariableFactory(WelcomeMessageConstant.MESSAGE_RELOAD)
                        .prefix(WelcomeMessageConstant.MESSAGE_PREFIX)
                        .date(WelcomeMessageConstant.SET_VARIABLE_DATEFORMAT)
                        .color()
                        .getString());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}