package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.welcomemessagecommand.WelcomeMessageCommand;
import me.xiaoying.sb.constant.WelcomeMessageConstant;
import me.xiaoying.sb.entity.WelcomeMessageEntity;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.WelcomeMessageListener;
import me.xiaoying.sb.service.WelcomeMessageService;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;

/**
 * 处理 WelcomeMessage
 */
public class WelcomeMessageHandle implements Handle {
    @Override
    public boolean enable() {
        return WelcomeMessageConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();

        ServerUtil.sendMessage("&b|    &a服务器欢迎消息(WelcomeMessage): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("ltp", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a服务器欢迎消息(WelcomeMessage): &c未开启", true);
    }

    @Override
    public void reload() {
        ServerBuild.getFileService().file("WelcomeMessage");
        ServerBuild.getFileService().init("WelcomeMessage");
        WelcomeMessageService.unregisterWelcomeMessages();

        if (!this.enable()) {
            PluginUtil.unregisterCommand("wm", ServerBuild.getInstance());
            return;
        }

        YamlUtil.getChildrenNode(ServerUtil.getDataFolder() + "/WelcomeMessage.yml").forEach(string -> {
            if (string.equalsIgnoreCase("set") || string.equalsIgnoreCase("Enable") || string.equalsIgnoreCase("Message") || string.equalsIgnoreCase("Use-Help"))
                return;

            WelcomeMessageService.registerWelcomeMessage(new WelcomeMessageEntity(string));
        });
        ServerUtil.registerCommand("wm", new WelcomeMessageCommand());
        ServerUtil.registerEvent(new WelcomeMessageListener());
    }
}