package me.xiaoying.serverbuild.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.module.JavaModule;
import me.xiaoying.serverbuild.module.Module;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PlaceholderModule extends PlaceholderExpansion {
    private final Module module;
    private final Map<String, SPlaceholder> placeholders = new HashMap<>();

    public PlaceholderModule(Module module) {
        this.module = module;
    }

    /**
     * Register child placeholder<br>
     * Eg: identifier_childKey -> serverbuild_hello
     *
     * @param placeholder SPlaceholder
     */
    private void registerPlaceholder(SPlaceholder placeholder) {
        this.placeholders.put(placeholder.getKey(), placeholder);
    }

    /**
     * Unregister placeholder by SPlaceholder's key
     *
     * @param childKey SPlaceholder's key
     */
    public void unregisterPlaceholder(String childKey) {
        this.placeholders.remove(childKey);
    }

    /**
     * Unregister placeholder by SPlaceholder
     *
     * @param placeholder SPlaceholder
     */
    public void unregisterPlaceholder(SPlaceholder placeholder) {
        Iterator<String> iterator = this.placeholders.keySet().iterator();
        String string;
        while (iterator.hasNext() && (string = iterator.next()) != null) {
            if (placeholder != this.placeholders.get(string))
                continue;

            iterator.remove();
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return this.module.getAliasName().toLowerCase(Locale.ENGLISH);
    }

    @Override
    public @NotNull String getAuthor() {
        List<String> authors;
        StringBuilder stringBuilder = new StringBuilder();

        if (this.module instanceof JavaModule)
            authors = ((JavaModule) this.module).getDescription().getAuthors();
        else
            authors = SBPlugin.getInstance().getDescription().getAuthors();

        for (int i = 0; i < authors.size(); i++) {
            stringBuilder.append(authors.get(i));

            if (i == authors.size() - 1)
                break;

            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    @Override
    public @NotNull String getVersion() {
        if (this.module instanceof JavaModule)
            return ((JavaModule) this.module).getDescription().getVersion();
        return SBPlugin.getInstance().getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (!this.placeholders.containsKey(params))
            return null;

        return this.placeholders.get(params).replace(player);
    }
}