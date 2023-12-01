package me.xiaoying.serverbuild.utils;

import com.alibaba.fastjson.JSON;

/**
 * 工具类 JSON
 */
public class JSONUtil {
    public static boolean isJson(String string) {
        try {
            JSON.parseObject(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}