package me.xiaoying.sb.service;

import me.xiaoying.sb.entity.BookContentEntity;
import me.xiaoying.sb.file.files.FileBookContent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookContentServer {
    private static final List<BookContentEntity> list = new ArrayList<>();

    public static void registerBookContent(String key) {
        String name = FileBookContent.bookContent.getString("BookContent." + key + ".Name");
        String content = FileBookContent.bookContent.getString("BookContent." + key + ".Content");

        list.add(new BookContentEntity(key, name, content));
    }

    public static void unregisterBookContent(String key) {
        Iterator<BookContentEntity> iterator = list.iterator();
        BookContentEntity entity;
        while ((entity = iterator.next()) != null) {
            if (!entity.getId().equalsIgnoreCase(key))
                continue;

            iterator.remove();
        }
    }

    public static void unregisterBookContents() {
        list.clear();
    }

    public static List<BookContentEntity> getBookContents() {
        return list;
    }
}