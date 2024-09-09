package me.xiaoying.serverbuild.utils;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {
    /**
     * 彩色文字
     *
     * @param text 文字
     * @return 替换后文字
     */
    public static String translate(String text) {
        text = ColorUtil.hexColor(text);
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String hexColor(String text) {
        if (!text.contains("#"))
            return text;

        Pattern six = Pattern.compile("#[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]");
        Matcher sixMatcher = six.matcher(text);

        int time = 0;
        while (sixMatcher.find()) {
            text = text.replace(sixMatcher.group(time), net.md_5.bungee.api.ChatColor.of(sixMatcher.group(time)).toString());
            time++;
        }

        Pattern three = Pattern.compile("#[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]");
        Matcher threeMatcher = three.matcher(text);

        time = 0;
        while (threeMatcher.find()) {
            String string = threeMatcher.group(time);
            String stringBuilder = String.valueOf(string.charAt(1)) + string.charAt(1) +
                    string.charAt(2) + string.charAt(2) +
                    string.charAt(3) + string.charAt(3);
            text = text.replace(string, net.md_5.bungee.api.ChatColor.of(stringBuilder).toString());
            time++;
        }
        return text;
    }
}