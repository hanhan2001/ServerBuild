package me.xiaoying.serverbuild.utils;

/**
 * 工具类 数字
 */
public class NumUtil {
    /**
     * 是否为数字
     *
     * @param str 字符串
     * @return 逻辑值
     */
    public static boolean isNum(String str) {
        return Character.isDigit(str.charAt(0));
    }

    /**
     * 是否为 Float
     *
     * @param value 字符串
     * @return 逻辑值
     */
    public static boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否为 Double
     *
     * @param value 字符串
     * @return 逻辑值
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}