package me.xiaoying.serverbuild.function.functions;

import me.xiaoying.serverbuild.constant.ConstantAutoReSpawn;
import me.xiaoying.serverbuild.file.SubFile;
import me.xiaoying.serverbuild.file.file.FileAutoReSpawn;
import me.xiaoying.serverbuild.function.Function;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Function AutoReSpawn
 */
public class AutoReSpawnFunction implements Function {
    private final List<SubFile> files = new ArrayList<>();
    {
        files.add(new FileAutoReSpawn());
    }
    @Override
    public String getName() {
        return "AutoReSpawn";
    }

    @Override
    public String getAliasName() {
        return "自动重生";
    }

    @Override
    public String getDescription() {
        return "为玩家自动重生";
    }

    @Override
    public boolean enable() {
        SubFile subFile = null;
        for (SubFile file : this.files) {
            if (!file.getName().equalsIgnoreCase("AutoReSpawn.yml"))
                continue;

            subFile = file;
        }
        return ((ConstantAutoReSpawn) subFile.getConstant()).ENABLE_AUTORESPAWN;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }

    @Override
    public List<SubFile> getFiles() {
        return this.files;
    }

    @Override
    public List<Listener> getListeners() {
        return null;
    }
}