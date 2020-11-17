package sdemirci.packagechallenge.exceptions;

public class ApplicationArgumentException extends Exception {
    public ApplicationArgumentException() {
        super();
    }

    public ApplicationArgumentException(String message) {
        super(message);
    }

    public ApplicationArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationArgumentException(Throwable cause) {
        super(cause);
    }
}
