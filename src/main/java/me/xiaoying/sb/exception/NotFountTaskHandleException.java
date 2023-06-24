package me.xiaoying.sb.exception;

public class NotFountTaskHandleException extends Exception {
    public NotFountTaskHandleException() {
        super();
    }

    public NotFountTaskHandleException(String message) {
        super(message);
    }

    public NotFountTaskHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFountTaskHandleException(Throwable cause) {
        super(cause);
    }

    protected NotFountTaskHandleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}