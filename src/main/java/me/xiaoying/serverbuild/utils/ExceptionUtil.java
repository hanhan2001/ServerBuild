package me.xiaoying.serverbuild.utils;

public class  ExceptionUtil {
    public static void throwException(Exception exception) {
        try {
            throw exception;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}