package main.java.com.wallet.repositories;

import main.java.com.wallet.database.WalletDatabase;
import main.java.com.wallet.entities.Transaction;
import main.java.com.wallet.entities.Wallet;

import java.util.List;

public class WalletRepository implements GenericRepository<Wallet>{

    private WalletDatabase databaseConnection;

    public WalletRepository() {
        this.databaseConnection = WalletDatabase.getDatabaseConnection();
    }

    public Wallet getActiveWalletByUserId(Integer userId){
        List<Wallet> wallets = this.databaseConnection.getWallets();
        for(Wallet w : wallets){
            if(w.getUserId() == userId && w.getActive()) return w;
        }
        return null;
    }

    public Integer saveOrUpdate(Wallet wallet) {
        Integer id;
        if(wallet.getId() == null){
            id = this.databaseConnection.getWallets().size();
            wallet.setId(id);
            this.databaseConnection.getWallets().add(wallet);
        }
        else {
            id = wallet.getId();
            this.databaseConnection.getWallets().set(id, wallet);
        }
        return id;
    }

    @Override
    public Wallet findById(Integer id) {
        return this.databaseConnection.getWallets().get(id);
    }
}
