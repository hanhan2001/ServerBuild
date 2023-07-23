package me.xiaoying.sb.exception;

public class PlayerDataException extends Exception {
    public PlayerDataException() {
        super();
    }

    public PlayerDataException(String message) {
        super(message);
    }

    public PlayerDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerDataException(Throwable cause) {
        super(cause);
    }

    protected PlayerDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}