package me.xiaoying.serverbuild.command.autorespawn;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.autorespawn.commands.ARReloadCommand;
import me.xiaoying.serverbuild.command.autorespawn.commands.ARRespawnPlayerCommand;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileAutoRespawn;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Command(values = "ar", length = 1)
public class AutoRespawnCommand extends SCommand {
    public AutoRespawnCommand() {
        this.registerCommand(new ARReloadCommand());
        this.registerCommand(new ARRespawnPlayerCommand());
    }

    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileAutoRespawn.MESSAGE_HELP)
                .prefix(FileAutoRespawn.SETTING_PREFIX)
                .date(FileAutoRespawn.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        this.parentCommand(sender, strings);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = super.onTabComplete(sender, command, head, strings);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player.getLocation(), "minecraft:block.bamboo.fall", 1F, 0F);
        }
        return list;
    }
}