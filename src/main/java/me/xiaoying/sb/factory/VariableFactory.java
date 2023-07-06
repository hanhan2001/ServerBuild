package me.xiaoying.sb.factory;


import me.clip.placeholderapi.PlaceholderAPI;
import me.xiaoying.sb.utils.ColorUtil;
import me.xiaoying.sb.utils.DateUtil;
import org.bukkit.entity.Player;

/**
 * 变量工厂
 */
public class VariableFactory {
    String string;

    public VariableFactory(String string) {
        this.string = string;
    }

    public VariableFactory player(Player player) {
        this.string = this.string.replace("%player%", player.getName());
        return this;
    }

    public VariableFactory player(String player) {
        this.string = this.string.replace("%player%", player);
        return this;
    }

    public VariableFactory date(String dateFormat) {
        this.string = this.string.replace("%date%", DateUtil.getDate(dateFormat));
        return this;
    }

    public VariableFactory prefix(String prefix) {
        this.string = this.string.replace("%prefix%", prefix);
        return this;
    }

    public VariableFactory placeholder(Player player) {
        this.string = PlaceholderAPI.setPlaceholders(player, this.string);
        return this;
    }

    public VariableFactory color() {
        this.string = ColorUtil.translate(this.string);
        return this;
    }

    public VariableFactory chatmessgae(String message) {
        this.string = this.string.replace("%message%", message);
        return this;
    }

    public VariableFactory file(String file) {
        this.string = this.string.replace("%file%", file);
        return this;
    }

    public VariableFactory time(String time) {
        this.string = this.string.replace("%time%", time);
        return this;
    }

    public String getString() {
        return this.string;
    }
}