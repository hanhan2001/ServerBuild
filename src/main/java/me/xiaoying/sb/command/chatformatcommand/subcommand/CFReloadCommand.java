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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class CFReloadCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.hasPermission("sb.cf.admin") && !sender.isOp()) {
            sender.sendMessage(ColorUtil.translate(new VariableFactory(ChatFormatConstant.MESSAGE_NOPERMISSION)
                            .prefix(ChatFormatConstant.MESSAGE_PREFIX)
                            .date(ChatFormatConstant.SET_VARIABLE_DATEFORMAT)
                            .color()
                            .getString()));
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

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}