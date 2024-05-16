package main.java.com.wallet.utils;

import main.java.com.wallet.entities.Transaction;

import java.util.Comparator;

public class TransactionSortUtils implements Comparator<Transaction> {
    String sortField;

    public TransactionSortUtils(String sortField) {
        this.sortField = sortField;
    }

    @Override
    public int compare(Transaction o1, Transaction o2) {
        if(this.sortField == "amount") return sortByAmount(o1, o2);
        else if(this.sortField == "time") return sortByTime(o1, o2);
        return 0;
    }

    private int sortByTime(Transaction o1, Transaction o2) {
        return o1.getTime().compareTo(o2.getTime());
    }

    private int sortByAmount(Transaction o1, Transaction o2) {
        return o1.getAmount() - o2.getAmount();
    }
}
