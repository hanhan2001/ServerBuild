package me.xiaoying.sb.service.scriptcommand;

import org.bukkit.entity.Player;

/**
 * 抽象接口 脚本指令
 */
public abstract class ScriptCommand {
    public abstract String command();

    /**
     * 定义接口
     *
     * @param args 参数
     */
    public abstract boolean performCommand(Player player, String[] args);
}