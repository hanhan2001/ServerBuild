package me.xiaoying.sb.handle;

/**
 * 接口 功能处理
 */
public interface Handle {
    boolean enable();
    void onEnable();
    void onDisable();
    void reload();
    void stop();
}