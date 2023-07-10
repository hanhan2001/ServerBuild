package me.xiaoying.sb.entity.messageannouncer;

import me.xiaoying.sb.constant.MessageAnnouncerConstant;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.file.files.FileMessageAnnouncer;
import me.xiaoying.sb.utils.YamlUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatAnnouncerEntity extends MessageAnnouncerEntity {
    List<String> message;

    public ChatAnnouncerEntity(String key) {
        super(key);
        message = YamlUtil.getStringList(FileMessageAnnouncer.messageAnnouncer, "MessageAnnouncer.Chat.Message." + key);
    }

    @Override
    public void send(Player player) {
        for (String s : this.message)
            player.sendMessage(new VariableFactory(s)
                            .prefix(MessageAnnouncerConstant.MESSAGE_PREFIX)
                            .date(MessageAnnouncerConstant.SET_VARIABLE_DATEFORMAT)
                            .player(player)
                            .placeholder(player)
                            .color()
                            .getString());
    }
}