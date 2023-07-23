package me.xiaoying.sb.entity;

import me.xiaoying.sb.file.files.FileTextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.Objects;

public class TextComponentEntity {
    String value,
            click_value;

    ClickEvent.Action click_action;
    HoverEvent.Action hover_action;

    public TextComponentEntity(String key) {
        this.value = FileTextComponent.textComponent.getString(key + ".Value");
        this.click_value = FileTextComponent.textComponent.getString(key + ".Click.Value");

        // Click 事件定义
        switch (Objects.requireNonNull(FileTextComponent.textComponent.getString(key + ".Click.Type")).toUpperCase()) {
            case "URL": {
                this.click_action = ClickEvent.Action.OPEN_URL;
                break;
            }
            case "FILE": {
                this.click_action = ClickEvent.Action.OPEN_FILE;
                break;
            }
            case "COMMAND": {
                this.click_action = ClickEvent.Action.RUN_COMMAND;
                break;
            }
            case "COPY": {
                this.click_action = ClickEvent.Action.COPY_TO_CLIPBOARD;
                break;
            }
            default: {
                this.click_action = null;
                break;
            }
        }

        // Hover 事件定义
        switch (Objects.requireNonNull(FileTextComponent.textComponent.getString(key + ".Hover.Type")).toUpperCase()) {
            case "TEXT": {
                this.hover_action = HoverEvent.Action.SHOW_TEXT;
                break;
            }
            case "ITEM": {
                this.hover_action = HoverEvent.Action.SHOW_ITEM;
                break;
            }
            case "ENTITY": {
                this.hover_action = HoverEvent.Action.SHOW_ENTITY;
                break;
            }
            default: {
                this.hover_action = null;
                break;
            }
        }
    }

    public String getValue() {
        return value;
    }

    public String getClick_value() {
        return click_value;
    }

    public ClickEvent.Action getClick_action() {
        return click_action;
    }

    public HoverEvent.Action getHover_action() {
        return hover_action;
    }
}