package me.xiaoying.serverbuild.function;

import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.utils.ServerUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Function Server
 */
public class FunctionService {
    Set<Function> functions = new HashSet<>();

    /**
     * 注册 Function
     *
     * @param function Function
     */
    public void registerFunction(Function function) {
        // 判断是否已存在 Function
        if (this.functions.contains(function)) {
            ServerUtil.sendMessage("&e已注册过Function " + function.getName() + " 请检查代码是否有重复操作", true);
            return;
        }
        this.functions.add(function);
        // 生成配置文件
        for (SubFile file : function.getFiles()) {
            file.file();
            file.initialize();
        }
    }

    /**
     * 取消注册 Function
     *
     * @param function Function
     */
    public void unregisterFunction(Function function) {
        this.functions.remove(function);
    }

    /**
     * 取消注册 Function
     *
     * @param name Function name
     */
    public void unregisterFunction(String name) {
        for (Function function : this.functions) {
            if (!function.getName().equalsIgnoreCase(name))
                continue;

            this.functions.remove(function);
        }
    }

    /**
     * 取消注册所有 Function
     */
    public void unregisterFunctions() {
        this.functions.clear();
    }

    /**
     * 获取 Function
     *
     * @param name Function name
     * @return Function
     */
    public Function getFunction(String name) {
        for (Function function : this.functions) {
            if (!function.getName().equalsIgnoreCase(name))
                continue;

            return function;
        }

        return null;
    }

    /**
     * 获取所有 Function
     *
     * @return ArrayList
     */
    public List<Function> getFunctions() {
        return new ArrayList<>(functions);
    }

    /**
     * 加载所有 Function
     */
    public void enableFunctions() {
        for (Function function : this.functions)
            function.onEnable();
    }

    /**
     * 卸载所有 Function
     */
    public void disableFunctions() {
        for (Function function : this.functions)
            function.onDisable();
    }
}