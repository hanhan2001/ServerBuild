package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.command.homecommand.DelHomeCommand;
import me.xiaoying.sb.command.homecommand.HomeAdminCommand;
import me.xiaoying.sb.command.homecommand.HomeCommand;
import me.xiaoying.sb.command.homecommand.SetHomeCommand;
import me.xiaoying.sb.constant.HomeConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.playerdata.datas.HomePlayerData;
import me.xiaoying.sb.service.HomeService;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;
import me.xiaoying.sb.utils.YamlUtil;

public class HomeHandle implements Handle {
    @Override
    public boolean enable() {
        return HomeConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        reload();

        ServerUtil.sendMessage("&b|    &a家(Home): &e已开启", true);
    }

    @Override
    public void onDisable() {
        stop();

        ServerUtil.sendMessage("&b|    &a家(Home): &c未开启", true);
    }

    @Override
    public void reload() {
        stop();

        ServerBuild.getFileService().file("Home");
        ServerBuild.getFileService().init("Home");

        YamlUtil.getChildrenNode(ServerUtil.getDataFolder() + "/Home.yml", "Homes.Groups").forEach(HomeService::registerGroupHomeSet);

        ServerBuild.getPlayerDataService().registerPlayerData("home", new HomePlayerData());

        if (!this.enable())
            this.stop();

        ServerUtil.registerCommand("home", new HomeCommand());
        ServerUtil.registerCommand("delhome", new DelHomeCommand());
        ServerUtil.registerCommand("sethome", new SetHomeCommand());
        ServerUtil.registerCommand("homeadmin", new HomeAdminCommand());
    }

    @Override
    public void stop() {
        PluginUtil.unregisterCommand("home", ServerBuild.getInstance());
        PluginUtil.unregisterCommand("delhome", ServerBuild.getInstance());
        PluginUtil.unregisterCommand("sethome", ServerBuild.getInstance());
        PluginUtil.unregisterCommand("delhome", ServerBuild.getInstance());
        PluginUtil.unregisterCommand("homeadmin", ServerBuild.getInstance());

        HomeService.unregisterGroupHomeSets();
        ServerBuild.getPlayerDataService().unregisterPlayerData("home");
    }
}