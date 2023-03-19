package me.xiaoying.sb.handle;

import me.xiaoying.sb.command.logintpcommand.LoginTPCommand;
import me.xiaoying.sb.files.config.FileLoginTp;
import me.xiaoying.sb.listener.LoginTPListener;
import me.xiaoying.sb.utils.ServerUtil;

/**
 * 处理 LoginTP
 */
public class LoginTPHandle implements Handle {
    @Override
    public boolean enable() {
        return FileLoginTp.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        ServerUtil.registerEvent(new LoginTPListener());
        ServerUtil.registerCommand("lt", new LoginTPCommand());
        ServerUtil.sendMessage("&b|    &a固定上线点(LoginTP): &e已开启", true);
    }

    @Override
    public void onDisable() {
        ServerUtil.sendMessage("&b|    &a固定上线点(LoginTP): &c未开启", true);
    }

    @Override
    public void reload() {
        FileLoginTp.fileConfig();

        if (this.enable())
            return;

        ServerUtil.registerEvent(new LoginTPListener());
        ServerUtil.registerCommand("lt", new LoginTPCommand());
    }
}