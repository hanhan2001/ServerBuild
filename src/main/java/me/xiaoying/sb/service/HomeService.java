package me.xiaoying.sb.service;

import me.xiaoying.sb.file.files.FileHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeService {
    private static final Map<String, Integer> groupHomes = new HashMap<>();

    public static void registerGroupHomeSet(String name) {
        groupHomes.put(name.toUpperCase(), FileHome.home.getInt("Homes.Groups." + name));
    }

    public static void unregisterGroupHomeSet(String name) {
        groupHomes.remove(name);
    }

    public static void unregisterGroupHomeSets() {
        groupHomes.clear();
    }

    public static List<String> getGroupHomes() {
        return new ArrayList<>(groupHomes.keySet());
    }

    public static int getGroupHome(String name) {
        return groupHomes.get(name);
    }
}