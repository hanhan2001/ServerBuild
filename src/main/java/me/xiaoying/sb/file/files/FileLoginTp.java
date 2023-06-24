package me.xiaoying.sb.file.files;

import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.constant.LoginTpConstant;
import me.xiaoying.sb.file.SubFile;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileLoginTp extends SubFile {
    public static YamlConfiguration loginTp;
    File file = new File(ServerUtil.getDataFolder(), "LoginTp.yml");

    @Override
    public void newFile() {
        if (!this.file.exists()) ServerUtil.saveResources("LoginTp.yml");
        loginTp = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public void delFile() {
        file.delete();
    }

    @Override
    public void initFile() {
        LoginTpConstant.SET_ENABLE = loginTp.getBoolean("Enable");

        LoginTpConstant.LOCATION_WORLD = ServerUtil.getWorld(loginTp.getString("Location.World"));
        LoginTpConstant.LOCATION_X = loginTp.getDouble("Location.X");
        LoginTpConstant.LOCATION_Y = loginTp.getDouble("Location.Z");
        LoginTpConstant.LOCATION_Z = loginTp.getDouble("Location.Y");
        LoginTpConstant.LOCATION_YAW = (float) loginTp.getDouble("Location.Yaw");
        LoginTpConstant.LOCATION_PITCH = (float) loginTp.getDouble("Location.Pitch");

        LoginTpConstant.MESSAGE_PREFIX = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_PREFIX : loginTp.getString("Message.Prefix");
        LoginTpConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_RELOAD : loginTp.getString("Message.Reload");
        LoginTpConstant.MESSAGE_USEPLAYER = loginTp.getString("Message.UsePlayer");
        LoginTpConstant.MESSAGE_NOPERMISSION = ConfigConstant.OVERALL_ENABLE && ConfigConstant.OVERALL_ENABLE_MESSAGE ? ConfigConstant.OVERALL_MESSAGE_NOPERMISSION : loginTp.getString("Message.NoPermission");

        LoginTpConstant.MESSAGE_HELP = loginTp.getStringList("Use-Help");
    }
}
