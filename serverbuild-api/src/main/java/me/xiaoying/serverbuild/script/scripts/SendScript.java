package me.xiaoying.serverbuild.script.scripts;

import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.script.Script;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Script send
 */
public class SendScript implements Script {
    @Override
    public String getName() {
        return "send";
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c错误的命令格式，应当为 &esend [player] 内容"));
            return;
        }

        Player player;
        if ((player = Bukkit.getServer().getPlayer(args[0])) == null) {
            if (args[0].contains("*"))
                return;

            sender.sendMessage(new VariableFactory("&c找不到玩家 &e" + args[0]).color().toString());
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(" ");
        }

        player.sendMessage(stringBuilder.toString().replace("\\n", "\n"));
    }

    @Override
    public boolean processFirst() {
        return false;
    }
}