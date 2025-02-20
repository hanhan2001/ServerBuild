package me.xiaoying.serverbuild.entity.resolvelag;

import java.util.List;

public class ResolveLagClearDownEntity {
    private final int count;
    private final String type;
    private final List<String> scripts;

    public ResolveLagClearDownEntity(int count, String type, List<String> scripts) {
        this.count = count;
        this.type = type;
        this.scripts = scripts;
    }

    public int getCount() {
        return this.count;
    }

    public String getType() {
        return this.type;
    }

    public List<String> getScripts() {
        return this.scripts;
    }

    public boolean match(int count) {
        switch (this.getType()) {
            case ">":
                return count > this.getCount();
            case ">=":
            case "=>":
                return count >= this.getCount();
            case "<":
                return count < this.getCount();
            case "<=":
            case "=<":
                return count <= this.getCount();
            case "=":
                return count == this.getCount();
        }
        return false;
    }
}