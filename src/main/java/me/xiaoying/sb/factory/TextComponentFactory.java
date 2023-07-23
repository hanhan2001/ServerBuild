package me.xiaoying.sb.factory;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * 聊天组件工厂
 */
public class TextComponentFactory {
    TextComponent textComponent = new TextComponent();

    public TextComponentFactory add(String string) {
        this.textComponent.addExtra(string);
        return this;
    }

    public TextComponentFactory add(BaseComponent baseComponent) {
        this.textComponent.addExtra(baseComponent);
        return this;
    }

    public TextComponentFactory setClick(ClickEvent event) {
        this.textComponent.setClickEvent(event);
        return this;
    }

    public TextComponentFactory setHover(HoverEvent event) {
        this.textComponent.setHoverEvent(event);
        return this;
    }

    public TextComponentFactory setText(String value) {
        this.textComponent.setText(value);
        return this;
    }

    public TextComponent getTextComponent() {
        return this.textComponent;
    }

    public TextComponent toTextComponent() {
        return this.textComponent;
    }

    public String getString() {
        return this.textComponent.getText();
    }

    @Override
    public String toString() {
        return this.textComponent.getText();
    }
}