package me.xiaoying.serverbuild.file.resolvelag;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagClearDownEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagEntityCheckerEntity;
import me.xiaoying.serverbuild.file.SFile;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.utils.YamlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileResolveLagEntityChecker extends SFile {
    public static boolean ENABLE;

    public static int ENTITY_CHECKER_INTERVAL;

    public static List<String> ENTITY_CHECKER_EXCLUDE_WORLD;

    public static boolean ENTITY_CHECKER_MESSAGE_ENABLE;

    public FileResolveLagEntityChecker() {
        super("resolvelag/EntityChecker.yml", SBPlugin.getInstance().getDataFolder() + "/ResolveLag");
    }

    @Override
    public void onLoad() {
        FileResolveLagEntityChecker.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileResolveLagEntityChecker.ENTITY_CHECKER_INTERVAL = this.getConfiguration().getInt("EntityChecker.IntervalTime");

        FileResolveLagEntityChecker.ENTITY_CHECKER_EXCLUDE_WORLD = this.getConfiguration().getStringList("EntityChecker.ExcludeWorld");

        FileResolveLagEntityChecker.ENTITY_CHECKER_MESSAGE_ENABLE = this.getConfiguration().getBoolean("EntityChecker.Message.Enable");

        ResolveLagModule module = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");

        // entities
        YamlUtil.getNodes(this.getFile(), "EntityChecker.Entities").forEach(object -> module.getEntityCheckerManager().addCheckerEntity(new ResolveLagEntityCheckerEntity(this.getConfiguration().getString("EntityChecker.Entities." + object + ".Key"), this.getConfiguration().getInt("EntityChecker.Entities." + object + ".Limit"), this.getConfiguration().getInt("EntityChecker.Entities." + object + ".Min"))));

        // clear down
        YamlUtil.getNodes(this.getFile(), "EntityChecker.Message.ClearDown").forEach(object -> module.getEntityCheckerManager().addClearDown(new ResolveLagClearDownEntity(Integer.parseInt(object.toString()), this.getConfiguration().getString("EntityChecker.Message.ClearDown." + object + ".Type"), new ArrayList<>(Arrays.asList(this.getConfiguration().getString("EntityChecker.Message.ClearDown." + object + ".Script").split("\n"))))));
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}