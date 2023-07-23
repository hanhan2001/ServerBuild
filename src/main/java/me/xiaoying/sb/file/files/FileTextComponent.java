package me.xiaoying.sb.file.files;

import me.xiaoying.sb.entity.TextComponentEntity;
import me.xiaoying.sb.factory.TextComponentFactory;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.service.TextComponentService;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

/**
 * 文件 TextComponent.yml
 */
public class FileTextComponent extends SubFile {
    public static YamlConfiguration textComponent;
    File file = new File(ServerUtil.getDataFolder(), "TextComponent.yml");

    @Override
    public void newFile() {
        if (!this.file.exists()) ServerUtil.saveResources("TextComponent.yml");
        textComponent = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void delFile() {
        this.file.delete();
    }

    @Override
    public void initFile() {
        System.out.println(this.file.getPath());
        YamlUtil.getChildrenNode(this.file.getPath()).forEach(string -> {
            TextComponentEntity entity = new TextComponentEntity(string);

            TextComponentFactory textComponentFactory = new TextComponentFactory();
            textComponentFactory.setText(entity.getValue());

            if (entity.getClick_action() != null)
                textComponentFactory.setClick(new ClickEvent(entity.getClick_action(), entity.getClick_value()));
            if (entity.getHover_action() != null)
                textComponentFactory.setHover(new HoverEvent(entity.getHover_action(), new ArrayList<>()));

            TextComponentService.registerTextComponent(string, textComponentFactory.toTextComponent());
        });
    }
}