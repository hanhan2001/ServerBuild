package me.xiaoying.serverbuild.script.scripts;

import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.script.Script;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class SudoScript implements Script {
    @Override
    public String getName() {
        return "sudo";
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(new VariableFactory( "&c错误的命令格式，应当为 &esudo [player] [chat/command] [内容]").color().toString());
            return;
        }

        Player player;
        if ((player = Bukkit.getServer().getPlayer(args[0])) == null) {
            sender.sendMessage(new VariableFactory("&c无法找到玩家 &e%player% &c.").player(args[0]).color().toString());
            return;
        }

        String type = args[1].toUpperCase(Locale.ENGLISH);
        if (!type.equalsIgnoreCase("CHAT") && !type.equalsIgnoreCase("COMMAND")) {
            sender.sendMessage(new VariableFactory( "&c错误的命令格式，应当为 &esudo [player] [chat/command] [内容]").color().toString());
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(", ");
        }

        switch (type) {
            case "CHAT":
                player.chat(stringBuilder.toString());
                break;
            case "COMMAND":
                player.performCommand(stringBuilder.toString());
                break;
        }
    }

    @Override
    public boolean processFirst() {
        return false;
    }
}