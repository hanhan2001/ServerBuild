package me.xiaoying.serverbuild.pluginmanager;

public class PluginResponse {
    private final boolean success;
    private final String message;

    public PluginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}