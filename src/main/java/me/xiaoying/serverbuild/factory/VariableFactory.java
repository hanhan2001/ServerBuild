package me.xiaoying.serverbuild.factory;

import me.clip.placeholderapi.PlaceholderAPI;
import me.xiaoying.serverbuild.utils.ColorUtil;
import org.bukkit.entity.Player;

/**
 * VariableFactory
 */
public class VariableFactory {
    String string;

    public VariableFactory(String string) {
        this.string = string;
    }

    public VariableFactory placeholder(Player player) {
        this.string = PlaceholderAPI.setPlaceholders(player, this.string);
        return this;
    }

    public VariableFactory player(String player) {
        this.string = this.string.replace("%player%", player);
        return this;
    }

    public VariableFactory player(Player player) {
        this.string = this.string.replace("%player%", player.getName());
        return this;
    }

    public VariableFactory color() {
        this.string = ColorUtil.translate(this.string);
        return this;
    }

    public String getString() {
        return this.string;
    }

    @Override
    public String toString() {
        return this.string;
    }
}