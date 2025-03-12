package me.xiaoying.serverbuild.script.scripts;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.script.Script;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Script title
 */
public class TitleScript extends Script {
    @Override
    public String getName() {
        return "title";
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();

        Player findPlayer;

        if (args.length == 0) {
            Bukkit.getServer().getConsoleSender().sendMessage(new VariableFactory("&c错误的命令格式，应当为 &etitle [player] title:subtitle").color().toString());
            return;
        }

        findPlayer = Bukkit.getServer().getPlayer(args[0]);
        if (findPlayer == null && sender instanceof Player)
            findPlayer = (Player) sender;
        if (findPlayer == null) {
            if (args[0].contains("*"))
                return;

            Bukkit.getServer().getConsoleSender().sendMessage(new VariableFactory("&c无法找到玩家 &e%player% &c.").player(args[0]).color().toString());
            return;
        }

        for (int i = 0; i < args.length; i++) {
            if (findPlayer != null && i == 0)
                continue;

            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(" ");
        }
        assert findPlayer != null;

        String title = null;
        String subtitle = null;
        int in = 10;
        int stay = 70;
        int out = 20;
        // json 格式
        if (this.isJson(stringBuilder.toString())) {
            JsonObject jsonObject = JsonParser.parseString(stringBuilder.toString()).getAsJsonObject();
            if (jsonObject.has("title"))
                title = jsonObject.get("title").getAsString();
            if (jsonObject.has("subtitle"))
                subtitle = jsonObject.get("subtitle").getAsString();
            if (jsonObject.has("in"))
                in = jsonObject.get("in").getAsInt();
            if (jsonObject.has("stay"))
                stay = jsonObject.get("stay").getAsInt();
            if (jsonObject.has("out"))
                out = jsonObject.get("out").getAsInt();

            findPlayer.sendTitle(title, subtitle, in, stay, out);
            return;
        }

        // 普通格式
        if (stringBuilder.toString().contains(":")) {
            String[] split = stringBuilder.toString().split(":");
            title = split[0];
            subtitle = split[1];
        } else
            title = stringBuilder.toString();

        findPlayer.sendTitle(title, subtitle, 10, 70, 20);
    }

    @Override
    public boolean processFirst() {
        return false;
    }

    private boolean isJson(String string) {
        try {
            JsonParser.parseString(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}