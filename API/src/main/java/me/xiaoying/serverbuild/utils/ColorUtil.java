package me.xiaoying.serverbuild.utils;

import org.bukkit.ChatColor;

import java.awt.*;
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
     * 渐变色
     *
     * @param text 文本
     * @param startHexColor 初始十六进制颜色
     * @param endHexColor 终止十六进制颜色
     * @return 替换后文本
     */
    public static String gradient(String text, String startHexColor, String endHexColor) {
        if (!startHexColor.startsWith("#") || !endHexColor.startsWith("#"))
            return text;

        if (startHexColor.length() != 4 && startHexColor.length() != 7)
            return text;

        if (endHexColor.length() != 4 && endHexColor.length() != 7)
            return text;

        Color startColor = ColorUtil.hexToJwt(startHexColor);
        Color endColor = ColorUtil.hexToJwt(endHexColor);

        assert startColor != null;
        assert endColor != null;

        int startR = startColor.getRed();
        int startG = startColor.getGreen();
        int startB = startColor.getBlue();

        int endR = endColor.getRed();
        int endG = endColor.getGreen();
        int endB = endColor.getBlue();

        int step = text.length();

        // 计算初始颜色到终止颜色间差值
        int sR = (endR - startR) / step;
        int sG = (endG - startG) / step;
        int sB = (endB - startB) / step;

        StringBuilder stringBuilder = new StringBuilder();
        // 文本长度作为步长
        String[] split = text.split("");
        for (int i = 0; i < split.length; i++)
            stringBuilder.append(ColorUtil.colorToHex(new Color(sR * i + startR, sG * i + startG, sB * i + startB))).append(split[i]);

        return ColorUtil.hexColor(stringBuilder.toString());
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

        for (String s : ColorUtil.getHexCode(text)) {
            if (s.length() < 4)
                continue;

            String color;
            String old = null;
            if (s.length() < 7) {
                color = "#" + s.charAt(1) + s.charAt(1) + s.charAt(2) + s.charAt(2) + s.charAt(3) + s.charAt(3);
                old = "#" + s.charAt(1) + s.charAt(2) + s.charAt(3);
            } else
                color = s;

            text = old == null ? text.replace(color, net.md_5.bungee.api.ChatColor.of(color).toString()) : text.replace(old, net.md_5.bungee.api.ChatColor.of(color).toString());
        }
        return text;
    }

    /**
     * 通过十六进制获取 java.awt.Color<br>
     * 文本开头必须为 #，如 #fff 或 #ffffff
     *
     * @param text 十六进制文本
     * @return java.awt.Color
     */
    public static Color hexToJwt(String text) {
        if (text.length() < 4)
            return null;

        int red, green, blue;
        if (text.length() < 7) {
            red = Integer.parseInt(String.valueOf(text.charAt(1)) + text.charAt(1), 16);
            green = Integer.parseInt(String.valueOf(text.charAt(2)) + text.charAt(2), 16);
            blue = Integer.parseInt(String.valueOf(text.charAt(3)) + text.charAt(3), 16);
        } else {
            red = Integer.parseInt(String.valueOf(text.charAt(1)), 16);
            green = Integer.parseInt(String.valueOf(text.charAt(2)), 16);
            blue = Integer.parseInt(String.valueOf(text.charAt(3)), 16);
        }

        return new Color(red, green, blue);
    }

    /**
     * 获取文本中十六进制颜色代码
     *
     * @param text 文本
     * @return 十六进制颜色代码列表
     */
    public static Set<String> getHexCode(String text) {
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

        Set<String> filter = new HashSet<>();
        for (String s : hexList) {
            if (s.length() < 3)
                continue;

            if (s.length() < 6)
                s = "#" + s.charAt(0) + s.charAt(1) + s.charAt(2);
            else
                s = "#" + s;

            filter.add(s);
        }
        return filter;
    }

    /**
     * 通过 java.awt.Color 获取十六进制
     *
     * @param color java.awt.Color
     * @return 十六进制颜色代码
     */
    private static String colorToHex(Color color) {
        String r, g, b;
        StringBuilder stringBuilder = new StringBuilder();
        r = Integer.toHexString(color.getRed());
        g = Integer.toHexString(color.getGreen());
        b = Integer.toHexString(color.getBlue());

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();

        stringBuilder.append("#");
        stringBuilder.append(r);
        stringBuilder.append(g);
        stringBuilder.append(b);
        //#0000FF
        return stringBuilder.toString();
    }
}