package me.xiaoying.serverbuild.script.scripts.bossbar;

import me.xiaoying.serverbuild.script.Script;
import org.bukkit.command.CommandSender;

public class BossBarLengthScript extends Script {
    @Override
    public String getName() {
        return "length";
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {

    }

    @Override
    public boolean processFirst() {
        return false;
    }
}