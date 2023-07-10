package me.xiaoying.sb.entity.messageannouncer;

import me.xiaoying.sb.constant.MessageAnnouncerConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.file.files.FileMessageAnnouncer;
import me.xiaoying.sb.utils.PlayerUtil;
import org.bukkit.entity.Player;

public class ActionbarAnnouncerEntity extends MessageAnnouncerEntity {
    String message;

    public ActionbarAnnouncerEntity(String key) {
        super(key);
        message = FileMessageAnnouncer.messageAnnouncer.getString("MessageAnnouncer.ActionBar.Message." + key);
    }

    @Override
    public void send(Player player) {
        PlayerUtil.sendActionbar(player, new VariableFactory(this.message)
                .prefix(MessageAnnouncerConstant.MESSAGE_PREFIX)
                .date(MessageAnnouncerConstant.SET_VARIABLE_DATEFORMAT)
                .player(player)
                .placeholder(player)
                .color()
                .getString());
    }
}