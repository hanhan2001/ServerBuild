package me.xiaoying.serverbuild.script;

import me.xiaoying.serverbuild.ServerBuild;
import me.xiaoying.serverbuild.script.interpreter.InterpreterService;
import me.xiaoying.serverbuild.script.scripts.ActionbarScript;
import me.xiaoying.serverbuild.script.scripts.SendScript;
import me.xiaoying.serverbuild.script.scripts.TitleScript;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * ScriptManager
 */
public class ScriptManager {
    Map<String, Script> knownScript = new HashMap<>();
    InterpreterService interpreterService = new InterpreterService();

    public ScriptManager() {
        this.registerScript(new SendScript(), ServerBuild.getInstance());
        this.registerScript(new TitleScript(), ServerBuild.getInstance());
        this.registerScript(new ActionbarScript(), ServerBuild.getInstance());
    }

    /**
     * 注册 Script
     *
     * @param script 脚本命令
     * @param plugin 来源插件
     */
    public void registerScript(Script script, Plugin plugin) {
        String command = plugin.getName().toLowerCase() + ":" + script.command();

        if (this.knownScript.containsKey(command))
            return;

        this.knownScript.put(command, script);

        // 别名
        if (script.alias() == null)
            return;

        for (String alias : script.alias()) {
            if (this.knownScript.containsKey(plugin.getName().toLowerCase() + ":" + alias))
                continue;

            this.knownScript.put(plugin.getName().toLowerCase() + ":" + alias, script);
        }
    }

    /**
     * 取消注册 Script
     *
     * @param script Script
     */
    public void unregisterScript(Script script) {
        this.knownScript.values().removeIf(script1 -> script1 == script);
    }

    /**
     * 取消注册 Script
     *
     * @param script Script 命令名称
     * @param plugin Plugin
     */
    public void unregisterScript(String script, Plugin plugin) {
        this.knownScript.remove(plugin.getName().toLowerCase() + ":" + script);
    }

    /**
     * 执行 Script
     * 使用线程池
     *
     * @param command String
     */
    public void callScript(String command) {
        ServerBuild.getTaskService().getScheduledExecutor().execute(() -> this.onCommand(command));
    }

    /**
     * 执行 Script
     *
     * @param command String
     */
    public void onCommand(String command) {
        command = command.toLowerCase();

        command = this.interpreterService.interpreter(command);
        if (command.contains("\n")) {
            for (String s : command.split("\n"))
                this.onCommand(s);
            return;
        }

        String[] split = command.split(" ");

        // 命令头处理
        String head = split[0];
        if (!head.contains(":"))
            head = "serverbuild:" + head;

        // 判断是否存在 Script 命令
        if (!this.knownScript.containsKey(head)) {
            ServerUtil.sendMessage("&c未知命令 &e" + head.split(":")[head.split(":").length - 1]+ " &c请检查配置文件", true);
            return;
        }

        String[] function = new ArrayList<>(Arrays.asList(split).subList(1, split.length)).toArray(new String[0]);
        this.knownScript.get(head).performCommand(function);
    }
}