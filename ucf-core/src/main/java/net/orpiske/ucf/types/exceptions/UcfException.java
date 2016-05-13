package net.orpiske.ucf.types.exceptions;

/**
 * Created by otavio on 5/13/16.
 */
public class UcfException extends Exception {

    public UcfException() {
        super();
    }

    public UcfException(String message) {
        super(message);
    }

    public UcfException(String message, Throwable cause) {
        super(message, cause);
    }

    public UcfException(Throwable cause) {
        super(cause);
    }
}
