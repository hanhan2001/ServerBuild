package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.logintpcommand.LoginTPCommand;
import me.xiaoying.sb.constant.LoginTpConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.LoginTPListener;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;

/**
 * 处理 LoginTP
 */
public class LoginTPHandle implements Handle {
    @Override
    public boolean enable() {
        return LoginTpConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();
        ServerUtil.sendMessage("&b|    &a固定上线点(LoginTP): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("ltp", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a固定上线点(LoginTP): &c未开启", true);
    }

    @Override
    public void reload() {
        ServerBuild.getFileService().file("LoginTp");
        ServerBuild.getFileService().init("LoginTp");

        if (!this.enable()) {
            PluginUtil.unregisterCommand("ltp", ServerBuild.getInstance());
            return;
        }

        ServerUtil.registerEvent(new LoginTPListener());
        ServerUtil.registerCommand("ltp", new LoginTPCommand());
    }
}