package me.xiaoying.sb.entity.messageannouncer;

import me.xiaoying.sb.constant.MessageAnnouncerConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.file.files.FileMessageAnnouncer;
import me.xiaoying.sb.utils.PlayerUtil;
import org.bukkit.entity.Player;

public class TitleAnnouncerEntity extends MessageAnnouncerEntity {
    String title = null, subtitle = null;

    public TitleAnnouncerEntity(String key) {
        super(key);
        title = FileMessageAnnouncer.messageAnnouncer.getString("MessageAnnouncer.Title.Message." + key + ".Title");
        subtitle = FileMessageAnnouncer.messageAnnouncer.getString("MessageAnnouncer.Title.Message." + key + ".SubTitle");
    }

    @Override
    public void send(Player player) {
        if (this.title != null)
            this.title = new VariableFactory(this.title)
                    .prefix(MessageAnnouncerConstant.MESSAGE_PREFIX)
                    .date(MessageAnnouncerConstant.SET_VARIABLE_DATEFORMAT)
                    .player(player)
                    .placeholder(player)
                    .color()
                    .getString();
        if (this.subtitle != null)
            this.subtitle = new VariableFactory(this.subtitle)
                    .prefix(MessageAnnouncerConstant.MESSAGE_PREFIX)
                    .date(MessageAnnouncerConstant.SET_VARIABLE_DATEFORMAT)
                    .player(player)
                    .placeholder(player)
                    .color()
                    .getString();

        PlayerUtil.sendTitle(player, this.title, this. subtitle);
    }
}