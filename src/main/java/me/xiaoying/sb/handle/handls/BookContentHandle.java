package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.bookcontentcommand.BookContentCommand;
import me.xiaoying.sb.constant.BookContentConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.service.BookContentServer;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;

public class BookContentHandle implements Handle {
    @Override
    public boolean enable() {
        return BookContentConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();
        ServerUtil.sendMessage("&b|    &a书本文章(BookContent): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("bc", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a书本文章(BookContent): &c未开启", true);
    }

    @Override
    public void reload() {
        this.stop();

        ServerBuild.getFileService().file("BookContent");
        ServerBuild.getFileService().init("BookContent");

        if (!this.enable())
            return;

        YamlUtil.getChildrenNode(ServerUtil.getDataFolder() + "/BookContent.yml", "BookContent").forEach(key -> BookContentServer.registerBookContent(key.toString()));

        ServerUtil.registerCommand("bc", new BookContentCommand());
    }

    @Override
    public void stop() {
        BookContentServer.unregisterBookContents();
        PluginUtil.unregisterCommand("bc", ServerBuild.getInstance());
    }
}