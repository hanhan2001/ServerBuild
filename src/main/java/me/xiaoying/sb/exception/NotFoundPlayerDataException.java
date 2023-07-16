package me.xiaoying.sb.exception;

public class NotFoundPlayerDataException extends Exception {
    public NotFoundPlayerDataException() {
        super();
    }

    public NotFoundPlayerDataException(String message) {
        super(message);
    }

    public NotFoundPlayerDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundPlayerDataException(Throwable cause) {
        super(cause);
    }

    protected NotFoundPlayerDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}