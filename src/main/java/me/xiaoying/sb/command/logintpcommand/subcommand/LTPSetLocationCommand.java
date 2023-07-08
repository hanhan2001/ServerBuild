package me.xiaoying.sb.command.logintpcommand.subcommand;

import me.xiaoying.sb.command.Command;
import me.xiaoying.sb.command.SubCommand;
import me.xiaoying.sb.constant.LoginTpConstant;
import me.xiaoying.sb.utils.ColorUtil;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Command(values = "set", length = 1)
public class LTPSetLocationCommand extends SubCommand {
    @Override
    public void registerCommand(SubCommand command) {

    }

    @Override
    public boolean performCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("sb.admin") && !sender.hasPermission("sb.lt.admin") && !sender.isOp()) {
            sender.sendMessage(ColorUtil.translate(LoginTpConstant.MESSAGE_PREFIX + LoginTpConstant.MESSAGE_NOPERMISSION));
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtil.translate(LoginTpConstant.MESSAGE_PREFIX + LoginTpConstant.MESSAGE_USEPLAYER));
            return false;
        }

        Player player = (Player) sender;
        Location location = player.getLocation();
        String world = Objects.requireNonNull(location.getWorld()).getName();
        String x = String.valueOf(location.getX());
        String y = String.valueOf(location.getY());
        String z = String.valueOf(location.getZ());
        String yaw = String.valueOf(location.getYaw());
        String pitch = String.valueOf(location.getPitch());

        YamlUtil.changeYamlContent(ServerUtil.getDataFolder() + "/LoginTP.yml", "Location.World", world);
        YamlUtil.changeYamlContent(ServerUtil.getDataFolder() + "/LoginTP.yml", "Location.X", x);
        YamlUtil.changeYamlContent(ServerUtil.getDataFolder() + "/LoginTP.yml", "Location.Y", y);
        YamlUtil.changeYamlContent(ServerUtil.getDataFolder() + "/LoginTP.yml", "Location.Z", z);
        YamlUtil.changeYamlContent(ServerUtil.getDataFolder() + "/LoginTP.yml", "Location.Yaw", yaw);
        YamlUtil.changeYamlContent(ServerUtil.getDataFolder() + "/LoginTP.yml", "Location.Pitch", pitch);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}