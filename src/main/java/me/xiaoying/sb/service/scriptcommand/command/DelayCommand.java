package me.xiaoying.sb.service.scriptcommand.command;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.service.scriptcommand.ScriptCommand;
import me.xiaoying.sb.utils.NumUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.entity.Player;

/**
 * 命令 延时命令
 */
public class DelayCommand extends ScriptCommand {
    @Override
    public String command() {
        return "delay";
    }

    @Override
    public boolean performCommand(Player player, String[] args) {
        if (!NumUtil.isNum(args[0])) {
            ServerUtil.sendMessage(new VariableFactory("&edelay &c参数必须为数字！")
                    .prefix(ConfigConstant.OVERALL_MESSAGE_PREFIX)
                    .getString(), true);
            return false;
        }
        int second = Integer.parseInt(args[0]);
        try {
            synchronized(this) {
                wait(second * 1000L);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}