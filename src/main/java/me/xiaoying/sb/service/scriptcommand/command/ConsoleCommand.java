package me.xiaoying.sb.service.scriptcommand.command;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.service.scriptcommand.ScriptCommand;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 命令 控制台
 */
public class ConsoleCommand extends ScriptCommand {
    @Override
    public String command() {
        return "console";
    }

    @Override
    public boolean performCommand(Player player, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if (i == args.length - 1) {
                stringBuilder.append(s);
                break;
            }
            stringBuilder.append(s).append(" ");
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                ServerUtil.dispatchCommand(new VariableFactory(stringBuilder.toString())
                                .player(player)
                                .prefix(ConfigConstant.OVERALL_MESSAGE_PREFIX)
                                .placeholder(player)
                                .color()
                        .getString());
            }
        }.runTaskLater(ServerBuild.getInstance(), 2L);
        return false;
    }
}