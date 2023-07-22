package me.xiaoying.sb.factory;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * 聊天组件工厂
 */
public class TextComponentFactory {
    TextComponent textComponent = new TextComponent();

    public TextComponentFactory string(String string) {
        this.textComponent.addExtra(string);
        return this;
    }

    public TextComponentFactory baseComponent(BaseComponent baseComponent) {
        this.textComponent.addExtra(baseComponent);
        return this;
    }

    public TextComponent getTextComponent() {
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