package main.java.com.wallet.database;

import main.java.com.wallet.entities.Transaction;
import main.java.com.wallet.entities.User;
import main.java.com.wallet.entities.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletDatabase {
    private static WalletDatabase connection;
    private List<User> users;
    private List<Wallet> wallets;
    private List<Transaction> transactions;

    private WalletDatabase(){
        this.wallets = new ArrayList<>();
        this.users = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public static WalletDatabase getDatabaseConnection(){
        if(connection == null) connection = new WalletDatabase();
        return connection;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
