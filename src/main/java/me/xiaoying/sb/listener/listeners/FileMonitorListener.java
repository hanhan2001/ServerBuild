package me.xiaoying.sb.listener.listeners;

import me.xiaoying.sb.ServerBuild;
import me.xiaoying.sb.constant.FileMonitorConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.handle.Handle;
import me.xiaoying.sb.handle.Handler;
import me.xiaoying.sb.utils.PlayerUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileMonitorListener extends FileAlterationListenerAdaptor {
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {

    }

    @Override
    public void onDirectoryCreate(File file) {
    }

    @Override
    public void onDirectoryChange(File file) {
    }

    @Override
    public void onDirectoryDelete(File file) {
    }

    @Override
    public void onFileCreate(File file) {
    }

    @Override
    public void onFileChange(File file) {
        // 文件过滤
        if (file.getName().equalsIgnoreCase("ServerBuild.db"))
            return;

        // console
        if (FileMonitorConstant.SET_MESSAGE_CONSOLE)
            ServerUtil.sendMessage(new VariableFactory(FileMonitorConstant.MESSAGE_UPDATE)
                    .prefix(FileMonitorConstant.MESSAGE_PREFIX)
                    .file(file.getName())
                    .date(FileMonitorConstant.SET_VARIABLE_DATEFORMAT).getString(), true);

        List<Player> playerList = new ArrayList<>();
        if (FileMonitorConstant.SET_MESSAGE_OPERATOR) {
            for (Player player : ServerUtil.getOnlinePlayers()) {
                if (!player.isOp() && !player.hasPermission("sb.fm.admin"))
                    continue;

                playerList.add(player);
            }
        }

        // chat
        if (FileMonitorConstant.SET_MESSAGE_OPERATOR_CHAT) {
            for (Player player : playerList) {
                for (String s : FileMonitorConstant.MESSAGE_OPERATOR_CHAT)
                    player.sendMessage(new VariableFactory(s)
                            .prefix(FileMonitorConstant.MESSAGE_PREFIX)
                            .date(FileMonitorConstant.SET_VARIABLE_DATEFORMAT)
                            .player(player)
                            .file(file.getName())
                            .placeholder(player)
                            .color()
                            .getString());
            }
        }

        // title
        if (FileMonitorConstant.SET_MESSAGE_OPERATOR_TITLE) {
            for (Player player : playerList) {
                String title = FileMonitorConstant.MESSAGE_OPERATOR_TITLE_TITLE;
                String subtitle = FileMonitorConstant.MESSAGE_OPERATOR_TITLE_SUBTITLE;
                if (title != null)
                    title = new VariableFactory(title)
                        .prefix(FileMonitorConstant.MESSAGE_PREFIX)
                        .file(file.getName())
                        .placeholder(player)
                        .date(FileMonitorConstant.SET_VARIABLE_DATEFORMAT)
                        .color()
                        .getString();
                if (subtitle != null)
                    subtitle = new VariableFactory(subtitle)
                            .prefix(FileMonitorConstant.MESSAGE_PREFIX)
                            .file(file.getName())
                            .date(FileMonitorConstant.SET_VARIABLE_DATEFORMAT)
                            .placeholder(player)
                            .color()
                            .getString();

                PlayerUtil.sendTitle(player, title, subtitle);
            }
        }

        // actionbar
        if (FileMonitorConstant.SET_MESSAGE_OPERATOR_ACTIONBAR) {
            for (Player player : playerList) {
                String message = FileMonitorConstant.MESSAGE_OPERATOR_ACTIONBAR;
                if (message != null)
                    message = new VariableFactory(message)
                            .prefix(FileMonitorConstant.MESSAGE_PREFIX)
                            .file(file.getName())
                            .date(FileMonitorConstant.SET_VARIABLE_DATEFORMAT)
                            .placeholder(player)
                            .color()
                            .getString();

                PlayerUtil.sendActionbar(player, message);
            }
        }

        String filename = file.getName().split("\\.")[0];
        if (filename.equalsIgnoreCase("config")) {
            ServerBuild.getFileService().file("Config");
            ServerBuild.getFileService().init("Config");
            return;
        }

        Handle handle = Handler.getHandle(filename);
        if (handle == null)
            return;

        handle.reload();
    }

    @Override
    public void onFileDelete(File file) {
    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {

    }
}
