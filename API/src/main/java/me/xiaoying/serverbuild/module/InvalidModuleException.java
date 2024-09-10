package me.xiaoying.serverbuild.module;

public class InvalidModuleException extends Exception {
    private static final long serialVersionUID = -8242141640709409544L;

    public InvalidModuleException(Throwable cause) {
        super(cause);
    }

    public InvalidModuleException() {
    }

    public InvalidModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidModuleException(String message) {
        super(message);
    }
}