package main.java.com.wallet.repositories;

import main.java.com.wallet.database.WalletDatabase;
import main.java.com.wallet.entities.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements GenericRepository<Transaction> {
    private WalletDatabase databaseConnection;

    public TransactionRepository() {
        this.databaseConnection = WalletDatabase.getDatabaseConnection();
    }
    @Override
    public Integer saveOrUpdate(Transaction transaction) {
        Integer id;
        if(transaction.getId() == null){
            id = this.databaseConnection.getUsers().size();
            transaction.setId(id);
            this.databaseConnection.getTransactions().add(transaction);
        }
        else {
            id = transaction.getId();
            this.databaseConnection.getTransactions().set(id, transaction);
        }
        return id;
    }

    @Override
    public Transaction findById(Integer id) {
        return this.databaseConnection.getTransactions().get(id);
    }

    public List<Transaction> getTransactionsForFilter(Integer id, String filter) {
        List<Transaction> allTransactions = this.databaseConnection.getTransactions();
        List<Transaction> retVal = new ArrayList<>();

        for(Transaction t : allTransactions){
            if((filter == null) && (t.getToUserId() == id || t.getFromUserId() == id)) retVal.add(t);
            else if(filter == "sent" && t.getFromUserId() == id) retVal.add(t);
            else if(filter == "received" && t.getToUserId() == id) retVal.add(t);
        }
        return retVal;
    }
}
