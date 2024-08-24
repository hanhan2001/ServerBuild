package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.command.chatformat.ChatFormatCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.ChatFormatEntity;
import me.xiaoying.serverbuild.file.FileChatFormat;
import me.xiaoying.serverbuild.listener.ChatFormatListener;
import me.xiaoying.serverbuild.utils.YamlUtil;
import me.xiaoying.sql.entity.Column;
import me.xiaoying.sql.sentence.Create;

import java.util.*;

/**
 * Module ChatFormat
 */
public class ChatFormatModule extends Module {
    private FileChatFormat file;
    private final Map<String, ChatFormatEntity> entityMap = new HashMap<>();

    @Override
    public String getName() {
        return "聊天格式";
    }

    @Override
    public String getAliasName() {
        return "ChatFormat";
    }

    @Override
    public boolean ready() {
        return FileChatFormat.ENABLE;
    }

    @Override
    public void init() {
        this.file = new FileChatFormat();
        // register files
        this.registerFile(this.file);

        // register listeners
        this.registerListener(new ChatFormatListener());

        // register commands
        this.registerCommand(new ChatFormatCommand());
    }

    @Override
    public void onEnable() {
        YamlUtil.getNodes(this.file.getFile().getPath(), "Formats").forEach(object -> {
            String string = object.toString();
            ChatFormatEntity entity = new ChatFormatEntity(string,
                    this.file.getConfiguration().getInt("Formats." + string + ".Priority"),
                    this.file.getConfiguration().getString("Formats." + string + ".Permission"),
                    this.file.getConfiguration().getString("Formats." + string + ".Format"));
            this.entityMap.put(string.toUpperCase(Locale.ENGLISH), entity);
        });
    }

    @Override
    public void onDisable() {
        this.entityMap.clear();
    }

    public ChatFormatEntity getChatFormatEntity(String name) {
        return this.entityMap.get(name.toUpperCase(Locale.ENGLISH));
    }

    public List<ChatFormatEntity> getChatFormatEntities() {
        return new ArrayList<>(this.entityMap.values());
    }

    public static void createTables() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("uuid", "varchar", 255, false));
        columns.add(new Column("save", "varchar", 255, false));
        columns.add(new Column("over", "varchar", 255, false));
        Create create = new Create(columns, FileChatFormat.TABLE_MUTE);
        SBPlugin.getSqlFactory().run(create);
    }
}