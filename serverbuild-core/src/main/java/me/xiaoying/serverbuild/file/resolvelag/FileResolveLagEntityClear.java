package me.xiaoying.serverbuild.file.resolvelag;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagClearDownEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagCountTimeEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagFilterEntity;
import me.xiaoying.serverbuild.file.SFile;
import me.xiaoying.serverbuild.manager.resolvelag.ResolveLagEntityClearManager;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.utils.YamlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileResolveLagEntityClear extends SFile {
    public static boolean ENABLE;

    public static int ENTITY_CLEAR_INTERVAL_TIME;

    public static boolean ENTITY_CLEAR_TOTAL_ENABLE;
    public static int ENTITY_CLEAR_TOTAL_LIMIT;

    public FileResolveLagEntityClear() {
        super("resolvelag/EntityClear.yml", SBPlugin.getInstance().getDataFolder() + "/ResolveLag");
    }

    @Override
    public void onLoad() {
        FileResolveLagEntityClear.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileResolveLagEntityClear.ENTITY_CLEAR_INTERVAL_TIME = this.getConfiguration().getInt("EntityClear.IntervalTime");

        FileResolveLagEntityClear.ENTITY_CLEAR_TOTAL_ENABLE = this.getConfiguration().getBoolean("EntityClear.Total.Enable");
        FileResolveLagEntityClear.ENTITY_CLEAR_TOTAL_LIMIT = this.getConfiguration().getInt("EntityClear.Total.Limit");

        ResolveLagEntityClearManager manager = ((ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag")).getEntityClearManager();

        // filters
        YamlUtil.getNodes(this.getFile(), "EntityClear.Filter").forEach(object -> {
            String string = object.toString();

            String type = this.getConfiguration().getString("EntityClear.Filter." + string + ".Type");
            List<String> values = this.getConfiguration().getStringList("EntityClear.Filter." + string + ".Values");

            int exclude = 0;
            if (YamlUtil.getNodes(this.getFile(), "EntityClear.Filter." + string).contains("Exclude"))
                exclude = this.getConfiguration().getInt("EntityClear.Filter." + string + ".Exclude");

            manager.addFilter(new ResolveLagFilterEntity(type, values, exclude));
        });

        // matchers
        YamlUtil.getNodes(this.getFile(), "EntityClear.Matcher").forEach(object -> {
            String string = object.toString();

            String type = this.getConfiguration().getString("EntityClear.Matcher." + string + ".Type");
            List<String> values = this.getConfiguration().getStringList("EntityClear.Matcher." + string + ".Values");

            int exclude = 0;
            if (YamlUtil.getNodes(this.getFile(), "EntityClear.Matcher." + string).contains("Exclude"))
                exclude = this.getConfiguration().getInt("EntityClear.Matcher." + string + ".Exclude");

            manager.addMatcher(new ResolveLagFilterEntity(type, values, exclude));
        });

        // count time
        YamlUtil.getNodes(this.getFile(), "EntityClear.CountTime").forEach(object -> manager.addCountTime(new ResolveLagCountTimeEntity(Integer.parseInt(object.toString()), new ArrayList<>(Arrays.asList(this.getConfiguration().getString("EntityClear.CountTime." + object).split("\n"))))));

        // clear down
        YamlUtil.getNodes(this.getFile(), "EntityClear.ClearDown").forEach(object -> manager.addClearDown(new ResolveLagClearDownEntity(Integer.parseInt(object.toString()), this.getConfiguration().getString("EntityClear.ClearDown." + object + ".Type"), new ArrayList<>(Arrays.asList(this.getConfiguration().getString("EntityClear.ClearDown." + object + ".Script").split("\n"))))));
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}