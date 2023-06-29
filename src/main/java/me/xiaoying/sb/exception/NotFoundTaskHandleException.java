package me.xiaoying.sb.exception;

public class NotFoundTaskHandleException extends Exception {
    public NotFoundTaskHandleException() {
        super();
    }

    public NotFoundTaskHandleException(String message) {
        super(message);
    }

    public NotFoundTaskHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundTaskHandleException(Throwable cause) {
        super(cause);
    }

    protected NotFoundTaskHandleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}