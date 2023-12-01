package me.xiaoying.serverbuild.script;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * ScriptManager
 */
public class ScriptManager {
    Map<String, Script> knownScript = new HashMap<>();

    /**
     * 注册 Script
     *
     * @param script 脚本命令
     * @param plugin 来源插件
     */
    public void registerScript(Script script, Plugin plugin) {

    }

    public void unregister(Script script) {

    }

    public void unregister(String command) {

    }


}