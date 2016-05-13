package net.orpiske.ucf.contrib.exceptions;

/**
 * Created by otavio on 5/13/16.
 */
public class ContribException extends Exception {
    public ContribException() {
        super();
    }

    public ContribException(String message) {
        super(message);
    }

    public ContribException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContribException(Throwable cause) {
        super(cause);
    }
}
