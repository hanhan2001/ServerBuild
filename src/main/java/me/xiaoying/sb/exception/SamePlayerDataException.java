package me.xiaoying.sb.exception;

public class SamePlayerDataException extends Exception {
    public SamePlayerDataException() {
        super();
    }

    public SamePlayerDataException(String message) {
        super(message);
    }

    public SamePlayerDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public SamePlayerDataException(Throwable cause) {
        super(cause);
    }

    protected SamePlayerDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}