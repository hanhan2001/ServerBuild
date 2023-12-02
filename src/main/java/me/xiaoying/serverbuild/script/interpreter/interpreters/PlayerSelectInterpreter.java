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
            return null;

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
        List<String> strings = new ArrayList<>();

        for (String s : StringUtil.rexStr(string, "*{", "}")) {
            String origin = s;
            s = StringUtil.removeSomeString(s, 0);
            s = StringUtil.removeSomeString(s, 0);
            s = StringUtil.removeSomeString(s, s.length() - 1);

            List<String> conditions = new ArrayList<>();
            for (String s1 : s.split(","))
                conditions.add(StringUtil.removeAllSpace(s1));

            List<Player> identity = new ArrayList<>();
            List<Player> permission = new ArrayList<>();
            for (String condition : conditions) {
                if (condition.startsWith("identity=")) {
                    String i = StringUtil.removeSomeString(condition, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    identity = this.identity(i);
                }
                if (condition.startsWith("permission=")) {
                    String i = StringUtil.removeSomeString(condition, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    i = StringUtil.removeSomeString(i, 0);
                    permission = this.permission(i);
                }
            }

            List<Player> players = new ArrayList<>();
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

            List<String> conditionStrings = new ArrayList<>();
            for (Player player : players)
                conditionStrings.add(string);

            int i = 0;
            for (String string1 : conditionStrings) {
                strings.add(string1.replace(origin, players.get(i).getName()));
                i++;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            stringBuilder.append(strings.get(i));

            if (i == strings.size() - 1)
                break;

            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}