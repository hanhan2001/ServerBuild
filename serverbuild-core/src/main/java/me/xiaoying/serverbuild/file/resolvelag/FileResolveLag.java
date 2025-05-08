package me.xiaoying.serverbuild.file.resolvelag;

import me.xiaoying.serverbuild.common.ConfigCommon;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagMemoryEntity;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagTPSEntity;
import me.xiaoying.serverbuild.file.SFile;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.utils.YamlUtil;

/**
 * File ResolveLag.yml
 */
public class FileResolveLag extends SFile {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT, SETTING_PREFIX;

    public static String RESOLVELAG_STATE_WORLD;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION,
            MESSAGE_UNKNOWN_WORLD,
            MESSAGE_STATE,
            MESSAGE_HELP;

    public FileResolveLag() {
        super("ResolveLag.yml");
    }

    @Override
    public void onLoad() {
        FileResolveLag.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileResolveLag.SETTING_DATEFORMAT = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        FileResolveLag.SETTING_PREFIX = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigCommon.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        FileResolveLag.RESOLVELAG_STATE_WORLD = this.getConfiguration().getString("ResolveLag.State.World");

        FileResolveLag.MESSAGE_RELOAD = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileResolveLag.MESSAGE_MISSING_PERMISSION = ConfigCommon.OVERALL_SITUATION_ENABLE && ConfigCommon.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigCommon.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        FileResolveLag.MESSAGE_HELP = this.getConfiguration().getString("Message.Help");

        FileResolveLag.MESSAGE_UNKNOWN_WORLD = this.getConfiguration().getString("Message.UnknownWorld");

        FileResolveLag.MESSAGE_STATE = this.getConfiguration().getString("Message.State");

        ResolveLagModule module = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");

        YamlUtil.getNodes(this.getFile(), "ResolveLag.State.Memory.Now").forEach(o -> {
            String string = o.toString();
            String type = this.getConfiguration().getString("ResolveLag.State.Memory.Now." + string + ".Type");
            String color = this.getConfiguration().getString("ResolveLag.State.Memory.Now." + string + ".Color");

            module.getMemoryManager().addNow(new ResolveLagMemoryEntity(Integer.parseInt(string), type, color));
        });

        YamlUtil.getNodes(this.getFile(), "ResolveLag.State.Memory.Idle").forEach(o -> {
            String string = o.toString();
            String type = this.getConfiguration().getString("ResolveLag.State.Memory.Idle." + string + ".Type");
            String color = this.getConfiguration().getString("ResolveLag.State.Memory.Idle." + string + ".Color");

            module.getMemoryManager().addIdle(new ResolveLagMemoryEntity(Integer.parseInt(string), type, color));
        });

        YamlUtil.getNodes(this.getFile(), "ResolveLag.State.Tps").forEach(o -> {
            String string = o.toString();

            String type = this.getConfiguration().getString("ResolveLag.State.Tps." + string + ".Type");
            String color = this.getConfiguration().getString("ResolveLag.State.Tps." + string + ".Color");

            module.getTpsManager().add(new ResolveLagTPSEntity(Integer.parseInt(string), type, color));
        });
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}