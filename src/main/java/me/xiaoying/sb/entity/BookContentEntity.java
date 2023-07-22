package me.xiaoying.sb.entity;

public class BookContentEntity {
    String id, name, author, title, content;

    public BookContentEntity(String id, String name, String author, String title, String content) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
}