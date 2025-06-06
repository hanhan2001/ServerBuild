package me.xiaoying.serverbuild.command.chatformat;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.chatformat.commands.CFMuteCommand;
import me.xiaoying.serverbuild.command.chatformat.commands.CFReloadCommand;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileChatFormat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Command(values = {"cf", "chatformat"}, length = {1, 3, 4}, description = "聊天格式命令")
public class ChatFormatCommand extends SCommand {
    public ChatFormatCommand() {
        this.registerCommand(new CFMuteCommand());
        this.registerCommand(new CFReloadCommand());
    }

    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileChatFormat.MESSAGE_HELP)
                        .prefix(FileChatFormat.SETTING_PREFIX)
                        .date(FileChatFormat.SETTING_DATEFORMAT)
                        .color()
                        .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        this.parentCommand(sender, strings);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = super.onTabComplete(sender, command, head, strings);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player.getLocation(), "minecraft:block.bamboo.fall", 1F, 0F);
        }
        return list;
    }
}