package me.xiaoying.serverbuild.module;

import java.util.List;

public class ModuleDescription {
    private final String name;
    private final String alias;
    private final String main;
    private final String version;
    private final List<String> authors;
    private final String description;

    public ModuleDescription(String name, String alias, String main, String version, List<String> author, String description) {
        this.name = name;
        this.alias = alias;
        this.main = main;
        this.version = version;
        this.authors = author;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getMain() {
        return this.main;
    }

    public String getVersion() {
        return this.version;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public String getDescription() {
        return this.description;
    }
}