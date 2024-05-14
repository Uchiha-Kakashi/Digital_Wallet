package main.java.com.wallet.repositories;

import main.java.com.wallet.database.WalletDatabase;
import main.java.com.wallet.entities.Transaction;
import main.java.com.wallet.entities.User;
import main.java.com.wallet.entities.Wallet;

import java.util.List;

public class UserRepository implements GenericRepository<User>{

    private WalletDatabase databaseConnection;

    public UserRepository() {
        this.databaseConnection = WalletDatabase.getDatabaseConnection();
    }

    public User getUserByUsername(String username) {
        List<User> users = this.databaseConnection.getUsers();
        for(User u : users){
            if(u.getUsername().equals(username)) return u;
        }
        return null;
    }

    @Override
    public Integer saveOrUpdate(User user) {
        Integer id;
        if(user.getId() == null){
            id = this.databaseConnection.getUsers().size();
            user.setId(id);
            this.databaseConnection.getUsers().add(user);
        }
        else {
            id = user.getId();
            this.databaseConnection.getUsers().set(id, user);
        }
        return id;
    }

    @Override
    public User findById(Integer id) {
        return this.databaseConnection.getUsers().get(id);
    }
}
