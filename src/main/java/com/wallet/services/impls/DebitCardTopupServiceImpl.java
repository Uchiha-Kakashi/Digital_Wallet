package main.java.com.wallet.services.impls;

import main.java.com.wallet.enums.TopupType;
import main.java.com.wallet.services.WalletTopupService;

public class DebitCardTopupServiceImpl implements WalletTopupService {

    private final TopupType topupType = TopupType.DEBIT_CARD;

    @Override
    public Boolean processPayment(Integer amount) {
        System.out.println("Adding amount - " + amount + " via Debit Card");
        return true;
    }

    @Override
    public TopupType getTopUpType() {
        return this.topupType;
    }
}
