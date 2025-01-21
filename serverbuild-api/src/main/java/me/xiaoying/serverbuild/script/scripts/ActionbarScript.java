package me.xiaoying.serverbuild.script.scripts;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.script.Script;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ActionbarScript implements Script {
    @Override
    public String getName() {
        return "actionbar";
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (args.length <= 1) {
            Bukkit.getServer().getConsoleSender().sendMessage(new VariableFactory("&c错误的命令格式，应当为 &eactionbar [player] 内容").color().toString());
            return;
        }

        Player player;
        if ((player = Bukkit.getServer().getPlayer(args[0])) == null) {
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

        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(SBPlugin.getInstance(), () -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(stringBuilder.toString())), 0L, 20L);
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
            Bukkit.getScheduler().cancelTask(i);
        });
    }

    @Override
    public boolean processFirst() {
        return false;
    }
}