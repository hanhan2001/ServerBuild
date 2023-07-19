package me.xiaoying.sb.handle.handls;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.ClearEntityConstant;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.listener.listeners.ClearEntityListener;
import me.xiaoying.sb.task.tasks.ClearEntityTask;
import me.xiaoying.sb.utils.PluginUtil;
import me.xiaoying.sb.utils.ServerUtil;

public class ClearEntityHandle implements Handle {
    @Override
    public boolean enable() {
        return ClearEntityConstant.SET_ENABLE;
    }

    @Override
    public void onEnable() {
        this.reload();

        ServerUtil.sendMessage("&b|    &a实体清理(ClearEntity): &e已开启", true);
    }

    @Override
    public void onDisable() {
        PluginUtil.unregisterCommand("ce", ServerBuild.getInstance());
        ServerUtil.sendMessage("&b|    &a实体清理(ClearEntity): &c已关闭", true);
    }

    @Override
    public void reload() {
        this.stop();

        ServerBuild.getFileService().file("ClearEntity");
        ServerBuild.getFileService().init("ClearEntity");

        if (!this.enable())
            return;

        ServerBuild.getTaskServer().registerTask(this, new ClearEntityTask());
        ServerBuild.getListenerService().registerListener(this, new ClearEntityListener());
    }

    @Override
    public void stop() {
        // 取消注册 Handle 命令
        PluginUtil.unregisterCommand("ce", ServerBuild.getInstance());

        // 取消注册 Handle 事件
        if (ServerBuild.getListenerService().getListeners(this) != null)
            ServerBuild.getListenerService().unregisterListener(this);
        // 取消注册 Handle 线程
        if (ServerBuild.getTaskServer().getTasks(this) != null)
            ServerBuild.getTaskServer().unregisterTasks(this);
    }
}