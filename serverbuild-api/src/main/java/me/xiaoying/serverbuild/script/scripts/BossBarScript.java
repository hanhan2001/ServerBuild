package me.xiaoying.serverbuild.script.scripts;

import me.xiaoying.serverbuild.script.Script;
import org.bukkit.command.CommandSender;

public class BossBarScript extends Script {
    @Override
    public String getName() {
        return "bossbar";
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        // bossbar create [name] [max_length] [now_length] [color] [style]

        // bossbar length [name] 20
        // bossbar color [name] color
        // bossbar style [name] style
        // bossbar hide [name] [player]
        // bossbar show [name] [player]
    }

    @Override
    public boolean processFirst() {
        return false;
    }
}