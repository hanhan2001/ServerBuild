package me.xiaoying.sb.exception;

public class NotFoundListenerException extends Exception {
    public NotFoundListenerException() {
        super();
    }

    public NotFoundListenerException(String message) {
        super(message);
    }

    public NotFoundListenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundListenerException(Throwable cause) {
        super(cause);
    }

    protected NotFoundListenerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
