package me.xiaoying.serverbuild.command.autorespawn.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import me.xiaoying.serverbuild.module.AutoRespawnModule;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class ARReloadCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileAutoRespawn.MESSAGE_HELP)
                .prefix(FileAutoRespawn.SETTING_PREFIX)
                .date(FileAutoRespawn.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (!sender.isOp() && !ServerUtil.hasPermission(sender, "sb.admin", "sb.ar.admin")) {
            sender.sendMessage(new VariableFactory(FileAutoRespawn.MESSAGE_MISSING_PERMISSION).prefix(FileAutoRespawn.SETTING_PREFIX).date(FileAutoRespawn.SETTING_DATEFORMAT).color().toString());
            return;
        }

        AutoRespawnModule module = (AutoRespawnModule) SBPlugin.getModuleManager().getModule("AutoRespawn");
        module.reload();

        sender.sendMessage(new VariableFactory(FileAutoRespawn.MESSAGE_RELOAD)
                .prefix(FileAutoRespawn.SETTING_PREFIX)
                .date(FileAutoRespawn.SETTING_DATEFORMAT)
                .color()
                .toString());
    }
}