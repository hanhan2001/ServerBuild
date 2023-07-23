package me.xiaoying.sb.exception;

public class TextComponentException extends Exception {
    public TextComponentException() {
        super();
    }

    public TextComponentException(String message) {
        super(message);
    }

    public TextComponentException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextComponentException(Throwable cause) {
        super(cause);
    }

    protected TextComponentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}