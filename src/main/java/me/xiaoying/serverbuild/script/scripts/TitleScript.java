package me.xiaoying.serverbuild.script.scripts;

import com.alibaba.fastjson.JSONObject;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.script.Script;
import me.xiaoying.serverbuild.utils.JSONUtil;
import me.xiaoying.serverbuild.utils.NumUtil;
import me.xiaoying.serverbuild.utils.PlayerUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Script Title
 */
public class TitleScript implements Script {
    @Override
    public String command() {
        return "title";
    }

    @Override
    public List<String> alias() {
        return null;
    }

    @Override
    public void performCommand(String[] args) {
        Player player = Bukkit.getServer().getPlayerExact(args[0]);
        if (player == null) {
            ServerUtil.sendMessage("&e" + args[0] + " &c玩家不在线", true);
            return;
        }

        // 拼接字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(" ");
        }

        String string = stringBuilder.toString();

        // 旧格式处理
        if (!JSONUtil.isJson(string)) {
            String[] spl = string.split(":");

            String title = spl[0], subtitle = null;

            if (spl.length == 2)
                subtitle = spl[1];

            if (subtitle != null) {
                PlayerUtil.sendTitle(player, new VariableFactory(title).color().toString(), new VariableFactory(subtitle).color().toString());
                return;
            }

            PlayerUtil.sendTitle(player, new VariableFactory(title).color().toString(), null);
        }

        // JSON 格式处理
        JSONObject jsonObject = JSONObject.parseObject(string);
        String title = null, subtitle = null;
        int in = 10, stay = 70, out = 20;

        // 主标题 副标题
        if (jsonObject.getString("title") != null && !jsonObject.getString("title").isEmpty())
            title = jsonObject.getString("title");
        if (jsonObject.getString("subtitle") != null && !jsonObject.getString("subtitle").isEmpty())
            subtitle = jsonObject.getString("subtitle");

        // 淡入 停留 淡出
        if (jsonObject.getString("in") != null && jsonObject.getString("in").isEmpty()) {
            if (NumUtil.isNum(jsonObject.getString("in")))
                in = jsonObject.getInteger("in");
        }
        if (jsonObject.getString("stay") != null && jsonObject.getString("stay").isEmpty()) {
            if (NumUtil.isNum(jsonObject.getString("stay")))
                in = jsonObject.getInteger("stay");
        }
        if (jsonObject.getString("out") != null && jsonObject.getString("out").isEmpty()) {
            if (NumUtil.isNum(jsonObject.getString("out")))
                in = jsonObject.getInteger("out");
        }

        PlayerUtil.sendTitle(player, title, subtitle, in, stay, out);
    }

    @Override
    public boolean processFirst() {
        return false;
    }
}