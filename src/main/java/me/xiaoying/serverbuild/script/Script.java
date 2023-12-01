package me.xiaoying.serverbuild.script;

import java.util.List;

/**
 * ScriptManager Script
 */
public interface Script {
    String command();
    List<String> alias();

    void performCommand(String[] args);
    boolean processFirst();
}