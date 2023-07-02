package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.logintpcommand.LoginTPCommand;
import me.xiaoying.sb.constant.LoginTpConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.listeners.LoginTPListener;
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
        stop();

        ServerBuild.getFileService().file("LoginTp");
        ServerBuild.getFileService().init("LoginTp");

        if (!this.enable()) {
            PluginUtil.unregisterCommand("ltp", ServerBuild.getInstance());
            return;
        }

        ServerBuild.getListenerService().registerListener(this, new LoginTPListener());
        ServerBuild.getListenerService().runListeners(this);
        ServerUtil.registerCommand("ltp", new LoginTPCommand());
    }

    @Override
    public void stop() {
        if (ServerBuild.getListenerService().getListeners(this) != null)
            ServerBuild.getListenerService().unregisterListener(this);
    }
}