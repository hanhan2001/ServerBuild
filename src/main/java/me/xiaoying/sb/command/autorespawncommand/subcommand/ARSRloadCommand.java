package me.xiaoying.sb.command.autorespawncommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.AutoReSpawnConstant;
import me.xiaoying.sb.constant.LoginTpConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.ColorUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class ARSRloadCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.hasPermission("sb.ars.admin") && !sender.isOp()) {
            sender.sendMessage(ColorUtil.translate(LoginTpConstant.MESSAGE_PREFIX + LoginTpConstant.MESSAGE_NOPERMISSION));
            return false;
        }

        Handle handler = Handler.getHandle("AutoReSpawn");
        handler.reload();

        sender.sendMessage(new VariableFactory(AutoReSpawnConstant.MESSAGE_RELOAD)
                .prefix(AutoReSpawnConstant.MESSAGE_PREFIX)
                .date(AutoReSpawnConstant.SET_VARIABLE_DATEFORMAT)
                .color()
                .getString());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}