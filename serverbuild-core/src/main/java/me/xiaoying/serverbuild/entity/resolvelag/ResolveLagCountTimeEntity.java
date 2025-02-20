package me.xiaoying.serverbuild.entity.resolvelag;

import java.util.List;

public class ResolveLagCountTimeEntity {
    private final int time;
    private final List<String> scripts;

    public ResolveLagCountTimeEntity(int time, List<String> scripts) {
        this.time = time;
        this.scripts = scripts;
    }

    public int getTime() {
        return this.time;
    }

    public List<String> getScripts() {
        return this.scripts;
    }
}