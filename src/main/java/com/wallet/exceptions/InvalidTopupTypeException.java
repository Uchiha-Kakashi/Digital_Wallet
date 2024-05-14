package main.java.com.wallet.exceptions;

public class InvalidTopupTypeException extends RuntimeException {
    public InvalidTopupTypeException(String message) {
        super(message);
    }
}
