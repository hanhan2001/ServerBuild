package me.xiaoying.serverbuild.script.scripts;

import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.script.Script;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Script Send
 */
public class SendScript implements Script {
    @Override
    public String command() {
        return "send";
    }

    @Override
    public List<String> alias() {
        return null;
    }

    @Override
    public void performCommand(String[] args) {
        Player player = Bukkit.getServer().getPlayerExact(args[0]);
        if (player == null) {
            ServerUtil.sendMessage("&e" + args[0] + " &c玩家不在线", true);
            return;
        }

        // 拼接字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(" ");
        }

        player.sendMessage(new VariableFactory(stringBuilder.toString())
                        .player(player)
                        .placeholder(player)
                        .color()
                        .toString());
    }

    @Override
    public boolean processFirst() {
        return false;
    }
}