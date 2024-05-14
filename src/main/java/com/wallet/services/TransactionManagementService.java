package main.java.com.wallet.services;

import main.java.com.wallet.entities.Transaction;
import main.java.com.wallet.entities.User;
import main.java.com.wallet.exceptions.UserDoesNotExistException;
import main.java.com.wallet.repositories.TransactionRepository;
import main.java.com.wallet.repositories.UserRepository;

import java.util.List;

public class TransactionManagementService {

    private static TransactionManagementService service = null;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    public static TransactionManagementService getInstance() {
        if (service == null) service = new TransactionManagementService();
        return service;
    }

    public TransactionManagementService() {
        this.transactionRepository = new TransactionRepository();
        this.userRepository = new UserRepository();
    }

    public Boolean createNewTransaction(User fromUser, User toUser, Integer transferAmount){
        Transaction transaction = new Transaction();
        transaction.setAmount(transferAmount);
        transaction.setFromUserId(fromUser.getId());
        transaction.setToUserId(toUser.getId());
        transaction.setSuccess(true);

        transactionRepository.saveOrUpdate(transaction);

        return transaction.getSuccess();
    }

    public void getAllUserTransactions(String username){
        User existingUser = userRepository.getUserByUsername(username);
        if(existingUser == null) throw new UserDoesNotExistException("User with name - " + username
                + " does not exist. Get Transactions Activity failed");

        List<Transaction> userTransactions = transactionRepository.getAllTransactions(existingUser.getId());

        System.out.println("Listing all the transactions for - " + username);
        for(Transaction transaction : userTransactions){
            String fromUsername = userRepository.findById(transaction.getFromUserId()).getUsername();
            String toUsername = userRepository.findById(transaction.getToUserId()).getUsername();
            System.out.println(transaction.toString(fromUsername, toUsername));
        }
    }

}
