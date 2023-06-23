package me.xiaoying.sb.handle;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.cache.Caches;
import me.xiaoying.sb.command.welcomemessagecommand.WelcomeMessageCommand;
import me.xiaoying.sb.entity.WelcomeMessageEntity;
import me.xiaoying.sb.files.config.FileWelcomeMessage;
import me.xiaoying.sb.listener.WelcomeMessageListener;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;

/**
 * 处理 WelcomeMessage
 */
public class WelcomeMessageHandle implements Handle {
    @Override
    public boolean enable() {
        return FileWelcomeMessage.SET_ENABLE;
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
        FileWelcomeMessage.fileWelcomeMessage();

        Caches.welcomeMessageEntities.clear();

        if (!this.enable()) {
            PluginUtil.unregisterCommand("wm", ServerBuild.getInstance());
            return;
        }

        YamlUtil.getChildrenNode(ServerUtil.getDataFolder() + "/WelcomeMessage.yml").forEach(string -> {
            if (string.equalsIgnoreCase("set") || string.equalsIgnoreCase("Enable") || string.equalsIgnoreCase("Message") || string.equalsIgnoreCase("Use-Help"))
                return;

            WelcomeMessageEntity welcomeMessageEntity = new WelcomeMessageEntity(string);
            Caches.welcomeMessageEntities.add(welcomeMessageEntity);
        });
        ServerUtil.registerCommand("wm", new WelcomeMessageCommand());
        ServerUtil.registerEvent(new WelcomeMessageListener());
    }
}