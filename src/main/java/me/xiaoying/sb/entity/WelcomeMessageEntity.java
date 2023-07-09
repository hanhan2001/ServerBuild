package me.xiaoying.sb.entity;

import me.xiaoying.sb.file.files.FileWelcomeMessage;
import me.xiaoying.sb.utils.YamlUtil;

import java.util.List;

/**
 * 实体 WelcomeMessage
 */
public class WelcomeMessageEntity {
    String key;
    
    String permission;
    int priority;

    // Join
    boolean JOIN_ENABLE_CHAT;
    boolean JOIN_ENABLE_TITLE;
    boolean JOIN_ENABLE_ACTIONBAR;

    boolean JOIN_ENABLE_TITLE_ALL;
    boolean JOIN_ENABLE_ACTIONBAR_ALL;

    List<String> JOIN_CHAT_MESSAGE;
    String JOIN_TITLE_TITLE, JOIN_TITLE_SUBTITLE;
    String JOIN_ACTIONBAR_MESSAGE;

    // Quit
    boolean QUIT_ENABLE_CHAT;
    boolean QUIT_ENABLE_TITLE;
    boolean QUIT_ENABLE_ACTIONBAR;


    List<String> QUIT_CHAT_MESSAGE;
    String QUIT_TITLE_TITLE, QUIT_TITLE_SUBTITLE;
    String QUIT_ACTIONBAR_MESSAGE;

    public WelcomeMessageEntity(String key) {
        this.key = key;
        
        priority = FileWelcomeMessage.welcomeMessage.getInt(key + ".Priority");
        permission = FileWelcomeMessage.welcomeMessage.getString(key + ".Permission");

        JOIN_ENABLE_CHAT = FileWelcomeMessage.welcomeMessage.getBoolean(key + ".Join.Chat.Enable");
        JOIN_ENABLE_TITLE = FileWelcomeMessage.welcomeMessage.getBoolean(key + ".Join.Title.Enable");
        JOIN_ENABLE_ACTIONBAR = FileWelcomeMessage.welcomeMessage.getBoolean(key + ".Join.ActionBar.Enable");
        JOIN_ENABLE_TITLE_ALL = FileWelcomeMessage.welcomeMessage.getBoolean(key + ".Join.Title.AllPlayer");
        JOIN_ENABLE_ACTIONBAR_ALL = FileWelcomeMessage.welcomeMessage.getBoolean(key + ".Join.ActionBar.AllPlayer");

        JOIN_CHAT_MESSAGE = YamlUtil.getStringList(FileWelcomeMessage.welcomeMessage, key + ".Join.Chat.Message");
        JOIN_TITLE_TITLE = FileWelcomeMessage.welcomeMessage.getString(key + ".Join.Title.Title");
        JOIN_TITLE_SUBTITLE = FileWelcomeMessage.welcomeMessage.getString(key + ".Join.Title.SubTitle");
        JOIN_ACTIONBAR_MESSAGE = FileWelcomeMessage.welcomeMessage.getString(key + ".Join.ActionBar.Message");

        QUIT_ENABLE_CHAT = FileWelcomeMessage.welcomeMessage.getBoolean(key + ".Quit.Chat.Enable");
        QUIT_ENABLE_TITLE = FileWelcomeMessage.welcomeMessage.getBoolean(key + ".Quit.Title.Enable");
        QUIT_ENABLE_ACTIONBAR = FileWelcomeMessage.welcomeMessage.getBoolean(key + ".Quit.ActionBar.Enable");

        QUIT_CHAT_MESSAGE = YamlUtil.getStringList(FileWelcomeMessage.welcomeMessage, key + ".Quit.Chat.Message");
        QUIT_TITLE_TITLE = FileWelcomeMessage.welcomeMessage.getString(key + ".Quit.Title.Title");
        QUIT_TITLE_SUBTITLE = FileWelcomeMessage.welcomeMessage.getString(key + ".Quit.Title.SubTitle");
        QUIT_ACTIONBAR_MESSAGE = FileWelcomeMessage.welcomeMessage.getString(key + ".Quit.Actionbar.Message");
    }
    
    public String getKey() {
        return this.key;
    }

    public String getPermission() {
        return this.permission;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean enableJoinChat() {
        return this.JOIN_ENABLE_CHAT;
    }

    public boolean enableJoinTitle() {
        return this.JOIN_ENABLE_TITLE;
    }

    public boolean enableJoinActionBar() {
        return this.JOIN_ENABLE_ACTIONBAR;
    }

    public boolean enableJoinTitleAllPlayer() {
        return this.JOIN_ENABLE_TITLE_ALL;
    }

    public boolean enableJoinActionbarAllPlayer() {
        return this.JOIN_ENABLE_ACTIONBAR_ALL;
    }

    public boolean enableQuitChat() {
        return this.QUIT_ENABLE_CHAT;
    }

    public boolean enableQuitTitle() {
        return this.QUIT_ENABLE_TITLE;
    }

    public boolean enableQuitActionBar() {
        return this.QUIT_ENABLE_ACTIONBAR;
    }

    public List<String> getJoinChatMessage() {
        return this.JOIN_CHAT_MESSAGE;
    }

    public String getJoinTitleTitle() {
        return this.JOIN_TITLE_TITLE;
    }

    public String getJoinTitleSubtitle() {
        return this.JOIN_TITLE_SUBTITLE;
    }

    public String getJoinActionbarMessage() {
        return this.JOIN_ACTIONBAR_MESSAGE;
    }

    public String getQuitTitleTitle() {
        return this.QUIT_TITLE_TITLE;
    }

    public String getQuitTitleSubtitle() {
        return this.QUIT_TITLE_SUBTITLE;
    }

    public String getQuitActionbarMessage() {
        return this.QUIT_ACTIONBAR_MESSAGE;
    }

    public List<String> getQuitChatMessage() {
        return this.QUIT_CHAT_MESSAGE;
    }
}