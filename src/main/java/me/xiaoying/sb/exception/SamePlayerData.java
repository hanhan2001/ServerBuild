package me.xiaoying.sb.exception;

public class SamePlayerData extends Exception {
    public SamePlayerData() {
        super();
    }

    public SamePlayerData(String message) {
        super(message);
    }

    public SamePlayerData(String message, Throwable cause) {
        super(message, cause);
    }

    public SamePlayerData(Throwable cause) {
        super(cause);
    }

    protected SamePlayerData(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}