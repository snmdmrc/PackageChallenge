package sdemirci.packagechallenge.exceptions;

public class DeserializeException extends Exception {
    public DeserializeException() {
        super();
    }

    public DeserializeException(String message) {
        super(message);
    }

    public DeserializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserializeException(Throwable cause) {
        super(cause);
    }
}
