package me.xiaoying.serverbuild.script.scripts.bossbar;

import me.xiaoying.serverbuild.script.Script;
import org.bukkit.command.CommandSender;

public class BossBarCreateScript extends Script {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {

    }

    @Override
    public boolean processFirst() {
        return false;
    }
}