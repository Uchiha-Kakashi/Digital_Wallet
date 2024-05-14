package main.java.com.wallet.services;

import main.java.com.wallet.enums.TopupType;

public interface WalletTopupService {
    public Boolean processPayment(Integer amount);
    public TopupType getTopUpType();
}
