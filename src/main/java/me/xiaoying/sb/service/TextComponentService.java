package me.xiaoying.sb.service;

import me.xiaoying.sb.exception.TextComponentException;
import me.xiaoying.sb.utils.ExceptionUtil;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * Service TextComponent
 */
public class TextComponentService {
    private static final Map<String, TextComponent> textComponents = new HashMap<>();

    /**
     * 注册 TextComponent
     *
     * @param id 标识id
     * @param textComponent TextComponent
     */
    public static void registerTextComponent(String id, TextComponent textComponent) {
        textComponents.put(id, textComponent);
    }

    /**
     * 取消注册 TextComponent
     *
     * @param id 标识id
     */
    public static void unregisterTextComponent(String id) {
        if (textComponents.get(id) == null)
            ExceptionUtil.throwException(new TextComponentException("Can't find '" + id + "' TextException, please check is registered this TextException"));
        textComponents.remove(id);
    }

    /**
     * 取消注册 TextComponent
     *
     * @param textComponent TextComponent
     */
    public static void unregisterTextComponent(TextComponent textComponent) {
        for (String s : textComponents.keySet()) {
            if (textComponents.get(s) != textComponent)
                continue;

            textComponents.remove(s);
        }

        ExceptionUtil.throwException(new TextComponentException("Can't find '" + textComponent.toString() + "' TextException, please check is registered this TextException"));
    }

    /**
     * 取消注册所有 TextComponent
     */
    public static void unregisterTextComponents() {
        textComponents.clear();
    }

    /**
     * 获取 TextComponent
     *
     * @param id 标识id
     * @return TextComponent
     */
    public static TextComponent getTextComponent(String id) {
        return textComponents.get(id);
    }
}