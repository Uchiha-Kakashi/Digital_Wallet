package main.java.com.wallet.exceptions;

public class NoActiveWalletException extends RuntimeException {
    public NoActiveWalletException(String message) {
        super(message);
    }
}
