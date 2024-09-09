package me.xiaoying.serverbuild.utils;

import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

public class ColorUtil {
    /**
     * 彩色文本
     *
     * @param text 文本
     * @return 替换后文本
     */
    public static String translate(String text) {
        text = ColorUtil.hexColor(text);
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * 十六进制彩色字体
     *
     * @param text 文本
     * @return 替换后文本
     */
    public static String hexColor(String text) {
        if (!text.contains("#"))
            return text;

        StringBuilder stringBuilder = new StringBuilder();
        boolean matching = false;
        Set<String> hexList = new HashSet<>();

        for (String s : text.split("")) {
            if (s.equalsIgnoreCase("#")) {
                matching = true;
                if (stringBuilder.length() <= 6) {
                    hexList.add(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                }
                continue;
            }

            if (matching) {
                if (!"0123456789AaBbCcDdEeFf".contains(s)) {
                    if (stringBuilder.length() <= 6) {
                        hexList.add(stringBuilder.toString());
                        stringBuilder.delete(0, stringBuilder.length());
                    }

                    matching = false;
                    stringBuilder.delete(0, stringBuilder.length());
                    continue;
                }
                stringBuilder.append(s);
            }

            if (stringBuilder.length() == 6) {
                hexList.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
                matching = false;
            }
        }

        for (String s : hexList) {
            if (s.length() < 3)
                continue;

            String color;
            String old = null;
            if (s.length() < 6) {
                color = "#" + s.charAt(0) + s.charAt(0) + s.charAt(1) + s.charAt(1) + s.charAt(2) + s.charAt(2);
                old = "#" + s.charAt(0) + s.charAt(1) + s.charAt(2);
            } else
                color = "#" + s;

            text = old == null ? text.replace(color, net.md_5.bungee.api.ChatColor.of(color).toString()) : text.replace(old, net.md_5.bungee.api.ChatColor.of(color).toString());
        }
        return text;
    }
}