package me.xiaoying.sb.command.homecommand;

import me.xiaoying.mf.SqlType;
import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.HomeConstant;
import me.xiaoying.sb.entity.HomeEntity;
import me.xiaoying.sb.factory.VariableFactory;

import me.xiaoying.sb.service.HomeService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令 Home setHome
 */
public class SetHomeCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(new VariableFactory(HomeConstant.MESSAGE_ERROR_IDENTITY)
                            .prefix(HomeConstant.MESSAGE_PREFIX)
                            .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                            .color()
                            .getString());
            return false;
        }

        String name;
        if (strings.length > 0)
            name = strings[0];
        else
            name = "home";

        Player player = (Player) sender;

        int max = 0;
        for (String groupHome : HomeService.getGroupHomes()) {
            if (!player.hasPermission("sb.home.group." + groupHome))
                continue;

            if (max < HomeService.getGroupHome(groupHome))
                max = HomeService.getGroupHome(groupHome);
        }
        if (player.isOp() || player.hasPermission("sb.home.group.unlimited"))
            max = 99999;

        // 判断是否存在相同名字的家
        List<HomeEntity> list = (List<HomeEntity>) ServerBuild.getPlayerDataService().getData("Home", "Home").getPlayerData(player);
        for (HomeEntity homeEntity : list) {
            if (!homeEntity.getName().equalsIgnoreCase(name))
                continue;

            sender.sendMessage(new VariableFactory(HomeConstant.MESSAGE_ALREADY_EXISTS)
                    .prefix(HomeConstant.MESSAGE_PREFIX)
                    .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                    .color()
                    .getString());
            return false;
        }

        // 判断是否超出权限许可家数量范围
        if (list.size() >= max) {
            sender.sendMessage(new VariableFactory(HomeConstant.MESSAGE_OVER_LIMIT)
                    .prefix(HomeConstant.MESSAGE_PREFIX)
                    .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                    .color()
                    .getString());
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i));

            if (i + 1 < list.size())
                stringBuilder.append(",");
        }

        // 创建新的家
        HomeEntity homeEntity = new HomeEntity(name, player);
        if (!stringBuilder.toString().isEmpty())
            stringBuilder.append(",");
        stringBuilder.append(homeEntity);

        if (((List<?>) ServerBuild.getPlayerDataService().getData("Home", "Home").getPlayerData(player)).size() == 0)
            ServerBuild.getPlayerDataService().getSqlFactory()
                    .type(SqlType.INSERT)
                    .table("home")
                    .insert(player.getName(), new HomeEntity(name, player).toString()).run();
        else
            ServerBuild.getPlayerDataService().getSqlFactory()
                    .table("home")
                    .type(SqlType.UPDATE)
                    .set("homes", stringBuilder.toString())
                    .condition("player", player.getName())
                    .run();

        player.sendMessage(new VariableFactory(HomeConstant.MESSAGE_SET_SUCCESS)
                .prefix(HomeConstant.MESSAGE_PREFIX)
                .date(HomeConstant.SET_VARIABLE_DATEFORMAT)
                .player(player)
                .placeholder(player)
                .color()
                .getString());
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }
}