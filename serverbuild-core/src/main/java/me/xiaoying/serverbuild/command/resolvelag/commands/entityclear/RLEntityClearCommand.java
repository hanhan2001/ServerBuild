package me.xiaoying.serverbuild.command.resolvelag.commands.entityclear;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLag;
import me.xiaoying.serverbuild.scheduler.resolvelag.ResolveLagEntityClearScheduler;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Command(values = "clear", length = {0, 1})
public class RLEntityClearCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileResolveLag.MESSAGE_HELP)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (!ServerUtil.hasPermission(sender) && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileResolveLag.MESSAGE_MISSING_PERMISSION)
                    .prefix(FileResolveLag.SETTING_PREFIX)
                    .date(FileResolveLag.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        List<World> worlds = new ArrayList<>();
        if (args.length == 0)
            worlds = Bukkit.getWorlds();
        else if (Bukkit.getServer().getWorld(args[0]) == null) {
            sender.sendMessage(new VariableFactory(FileResolveLag.MESSAGE_UNKNOWN_WORLD).prefix(FileResolveLag.SETTING_PREFIX).date(FileResolveLag.SETTING_DATEFORMAT).color().toString());
            return;
        } else
            worlds.add(Bukkit.getServer().getWorld(args[0]));

        ResolveLagEntityClearScheduler resolveLagEntityClearScheduler = new ResolveLagEntityClearScheduler();

        int amount = 0;
        for (World world : worlds)
            amount += resolveLagEntityClearScheduler.clear(world);

        resolveLagEntityClearScheduler.clearDown(amount);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1)
            Bukkit.getServer().getWorlds().forEach(world -> list.add(world.getName()));

        List<String> conditionList = new ArrayList<>();
        for (String s : list) {
            if (!s.toUpperCase(Locale.ENGLISH).startsWith(strings[strings.length - 1].toUpperCase(Locale.ENGLISH)))
                continue;

            conditionList.add(s);
        }
        return conditionList;
    }
}