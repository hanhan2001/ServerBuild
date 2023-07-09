package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.constant.NotBuildConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileNotBuild extends SubFile {
    public static YamlConfiguration notbuild;
    File file = new File(ServerUtil.getDataFolder(), "NotBuild.yml");

    @Override
    public void newFile() {
        if (!this.file.exists()) ServerUtil.saveResources("NotBuild.yml");
        notbuild = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void delFile() {
        file.delete();
    }

    @Override
    public void initFile() {
        NotBuildConstant.SET_ENABLE = notbuild.getBoolean("Enable");
        NotBuildConstant.SET_BUILD_ENABLE = notbuild.getBoolean("Build.Enable");
        NotBuildConstant.SET_DESTRUCTION_ENABLE = notbuild.getBoolean("Destruction.Enable");

        NotBuildConstant.SET_VARIABLE_DATEFORMAT = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : notbuild.getString("Set.DateFormat");
        NotBuildConstant.MESSAGE_BUILD = YamlUtil.getStringList(FileNotBuild.notbuild, "Destruction.Message");
        NotBuildConstant.MESSAGE_DESTRUCTION = YamlUtil.getStringList(FileNotBuild.notbuild, "Build.Message");
        NotBuildConstant.MESSAGE_HELP = notbuild.getStringList("Use-Help");

        NotBuildConstant.BUILD_WORLDS = notbuild.getStringList("Build.World");
        NotBuildConstant.DESTRUCTION_WORLDS = notbuild.getStringList("Destruction.World");

        NotBuildConstant.MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : notbuild.getString("Message.Prefix");
        NotBuildConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : notbuild.getString("Message.Reload");
        NotBuildConstant.MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : notbuild.getString("Message.NoPermission");
    }
}
