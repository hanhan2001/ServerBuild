package me.xiaoying.sb.entity;

import java.util.List;

public class ClearEntityEntity {
    int count;
    String type;
    List<String> message;

    public ClearEntityEntity(int count, String type, List<String> message) {
        this.count = count;
        this.type = type;
        this.message = message;
    }

    public int getCount() {
        return this.count;
    }

    public String getType() {
        return this.type;
    }

    public List<String> getMessage() {
        return this.message;
    }
}