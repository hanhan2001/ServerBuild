package me.xiaoying.serverbuild.script.interpreter.interpreters;

import me.xiaoying.serverbuild.script.interpreter.Interpreter;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.utils.StringUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * InterpreterService PlayerSelect
 */
public class PlayerSelectInterpreter implements Interpreter {
    /**
     * 身份判断
     *
     * @param identity 身份
     * @return ArrayList
     */
    private List<Player> identity(String identity) {
        if (!identity.equalsIgnoreCase("op") && !identity.equalsIgnoreCase("player"))
            return new ArrayList<>();

        List<Player> players = new ArrayList<>();
        for (Player onlinePlayer : ServerUtil.getOnlinePlayers()) {
            if (!onlinePlayer.isOp())
                continue;

            players.add(onlinePlayer);
        }
        return players;
    }

    /**
     * 权限判断
     *
     * @param permission 权限
     * @return ArrayList
     */
    private List<Player> permission(String permission) {
        List<Player> players = new ArrayList<>();

        for (Player onlinePlayer : ServerUtil.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission(permission))
                continue;

            players.add(onlinePlayer);
        }
        return players;
    }

    @Override
    public String interpret(String string) {
        StringBuilder interpretedString = new StringBuilder();

        for (String s : StringUtil.rexStr(string, "*{", "}")) {
            String origin = s;
            s = StringUtil.removeSomeString(s, 0);
            s = StringUtil.removeSomeString(s, 0);
            s = StringUtil.removeSomeString(s, s.length() - 1);

            List<String> conditions = new ArrayList<>();
            // 添加判断条件
            // 不含 = 则省略
            for (String s1 : s.split(",")) {
                if (!s1.contains("="))
                    continue;

                conditions.add(s1);
            }

            List<Player> identity = new ArrayList<>();
            List<Player> permission = new ArrayList<>();
            // 获取满足要求的玩家
            for (String condition : conditions) {
                String[] split = condition.split("=");
                String type = split[0];
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < split.length; i++) {
                    stringBuilder.append(split[i]);

                    if (i == split.length - 1)
                        break;

                    stringBuilder.append("=");
                }
                String content = stringBuilder.toString();

                if (type.equalsIgnoreCase("identity"))
                    identity = this.identity(content);
                if (type.equalsIgnoreCase("permission"))
                    permission = this.permission(content);
            }

            List<Player> players = new ArrayList<>();
            // 获取两个 ArrayList 都满足的玩家
            if (identity.size() != 0 && permission.size() != 0) {
                for (Player player : identity) {
                    if (!permission.contains(player))
                        continue;

                    players.add(player);
                }
            } else if (identity.size() != 0)
                players = identity;
            else
                players = permission;

            List<String> listString = new ArrayList<>();
            for (Player player : players) {
                String[] split = string.split(origin.replace("*", "\\*").replace(".", "\\.").replace("{", "\\{"));
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    stringBuilder.append(split[i]);

                    if (i == split.length - 1)
                        break;

                    stringBuilder.append(player.getName());
                }

                listString.add(stringBuilder.toString());
            }

            for (int i = 0; i < listString.size(); i++) {
                interpretedString.append(listString.get(i));

                if (i == listString.size() - 1)
                    break;

                interpretedString.append("\n");
            }
        }
        return interpretedString.toString();
    }
}