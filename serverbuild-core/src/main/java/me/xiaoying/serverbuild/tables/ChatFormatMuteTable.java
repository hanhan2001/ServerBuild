package me.xiaoying.serverbuild.tables;

import me.xiaoying.serverbuild.file.FileChatFormat;
import me.xiaoying.serverbuild.utils.DateUtil;
import me.xiaoying.sqlfactory.annotation.Column;
import me.xiaoying.sqlfactory.annotation.Param;
import me.xiaoying.sqlfactory.annotation.Table;

import java.util.Date;

@Table(name = "cf_mute")
public class ChatFormatMuteTable {
    // 设置 final 后会自定添加判断条件
    @Column(length = 255)
    private final String uuid;

    @Column(length = 255)
    private String save;

    @Column(length = 255)
    private String over;

    public ChatFormatMuteTable(@Param("uuid") String uuid, @Param("save") String save, @Param("over") String over) {
        this.uuid = uuid;
        this.save = save;
        this.over = over;
    }

    /**
     * 是否超过禁言时间
     *
     * @return Boolean
     */
    public boolean overdue() {
        return this.getDistance().getTime() >= 0;
    }

    /**
     * 获取剩余禁言时间
     *
     * @return Date
     */
    public Date getDistance() {
        return new Date(DateUtil.getDateReduce(new Date(), DateUtil.parse(this.over, FileChatFormat.SETTING_DATEFORMAT)));
    }
}