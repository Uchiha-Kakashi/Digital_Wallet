package main.java.com.wallet.services.impls;

import main.java.com.wallet.enums.TopupType;
import main.java.com.wallet.services.WalletTopupService;

public class UpiTopupServiceImpl implements WalletTopupService {
    private final TopupType topupType = TopupType.UPI;

    @Override
    public Boolean processPayment(Integer amount) {
        System.out.println("Adding amount - " + amount + " via UPI");
        return true;
    }

    @Override
    public TopupType getTopUpType() {
        return this.topupType;
    }
}
