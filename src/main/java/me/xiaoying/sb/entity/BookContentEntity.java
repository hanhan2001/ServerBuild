package me.xiaoying.sb.entity;

public class BookContentEntity {
    String id, name, content;

    public BookContentEntity(String id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }
}