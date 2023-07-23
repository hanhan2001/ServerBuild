package me.xiaoying.sb.exception;

public class TaskHandleException extends Exception {
    public TaskHandleException() {
        super();
    }

    public TaskHandleException(String message) {
        super(message);
    }

    public TaskHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskHandleException(Throwable cause) {
        super(cause);
    }

    protected TaskHandleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}