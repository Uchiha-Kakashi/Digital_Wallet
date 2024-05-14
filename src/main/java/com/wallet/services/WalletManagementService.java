package main.java.com.wallet.services;

import main.java.com.wallet.entities.User;
import main.java.com.wallet.entities.Wallet;
import main.java.com.wallet.enums.TopupType;
import main.java.com.wallet.exceptions.*;
import main.java.com.wallet.repositories.UserRepository;
import main.java.com.wallet.repositories.WalletRepository;
import main.java.com.wallet.services.impls.CreditCardTopupServiceImpl;
import main.java.com.wallet.services.impls.DebitCardTopupServiceImpl;
import main.java.com.wallet.services.impls.UpiTopupServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletManagementService {
    private static WalletManagementService service = null;
    Map<TopupType, WalletTopupService> topupServiceMap = new HashMap<>();
    private List<WalletTopupService> topupServices = new ArrayList<>();

    private WalletRepository walletRepository;
    private UserRepository userRepository;
    private TransactionManagementService transactionManagementService;


    public WalletManagementService() {
        this.walletRepository = new WalletRepository();
        this.userRepository = new UserRepository();
        this.transactionManagementService = new TransactionManagementService();
        addAllTopupServices();
        mapTopupServices();
    }

    private void mapTopupServices() {
        for(WalletTopupService topupService : topupServices){
            topupServiceMap.put(topupService.getTopUpType(), topupService);
        }
    }

    private void addAllTopupServices() {
        this.topupServices.add(new UpiTopupServiceImpl());
        this.topupServices.add(new CreditCardTopupServiceImpl());
        this.topupServices.add(new DebitCardTopupServiceImpl());
    }

    public static WalletManagementService getInstance(){
        if(service == null) service = new WalletManagementService();
        return service;
    }

    public Integer topupAmountToWallet(String username, String topupType, Integer amount){

        User existingUser = userRepository.getUserByUsername(username);
        if(existingUser == null) throw new UserDoesNotExistException("User with name - " + username
                + " does not exist. Topup activity failed");

        TopupType type =  null;
        try {
            type = TopupType.valueOf(topupType);
        } catch (IllegalArgumentException ex){
            throw new InvalidTopupTypeException(topupType + " is not supported. Please choose other method for topup");
        }

        if(amount <= 0) throw new InvalidAmountException("Invalid amount entered for topup - " + amount);

        WalletTopupService topupService = topupServiceMap.getOrDefault(type,null);

        if(topupService != null){
            Boolean paymentProcessed = topupService.processPayment(amount);
            if(paymentProcessed) return addAmountToWallet(existingUser, amount);
        }

        return -1;
    }

    private Integer addAmountToWallet(User existingUser, Integer amount) {
        System.out.println("Adding amount to wallet - " + amount);
        Wallet existingWallet = walletRepository.getActiveWalletByUserId(existingUser.getId());
        if(existingWallet == null) throw new NoActiveWalletException("No Active wallet exists for user - "  + existingUser.getUsername());

        existingWallet.setAmount(existingWallet.getAmount() + amount);
        walletRepository.saveOrUpdate(existingWallet);

        return existingWallet.getAmount();
    }

    private Integer deductAmountFromWallet(User user, Integer amount){
        System.out.println("Deducting amount from wallet");
        Wallet existingWallet = walletRepository.getActiveWalletByUserId(user.getId());
        if(existingWallet == null) throw new NoActiveWalletException("No Active wallet exists for user - "  + user.getUsername());

        existingWallet.setAmount(existingWallet.getAmount() - amount);
        walletRepository.saveOrUpdate(existingWallet);

        return existingWallet.getAmount();
    }

    public void sendMoney(String fromUsername, String toUsername, Integer transferAmount){
        User existingFromUser = userRepository.getUserByUsername(fromUsername);
        if(existingFromUser == null) throw new UserDoesNotExistException("Sender with name - " + fromUsername
                + " does not exist. Send Money Activity Failed");

        User existingToUser = userRepository.getUserByUsername(toUsername);
        if(existingToUser == null) throw new UserDoesNotExistException("Receiver with name - " + toUsername
                + " does not exist. Send Money Activity Failed");

        Wallet fromWallet = walletRepository.getActiveWalletByUserId(existingFromUser.getId());
        if(fromWallet.getAmount() < transferAmount) throw new InsufficientBalanceException("Insufficient " +
                "Balance for the transaction. Transaction Failed");


        Boolean success = transactionManagementService.createNewTransaction(existingFromUser, existingToUser, transferAmount);

        if(success){
            addAmountToWallet(existingToUser, transferAmount);
            deductAmountFromWallet(existingFromUser, transferAmount);
        }

    }

    public void createNewWallet(User user) {
        Wallet existingWallet = walletRepository.getActiveWalletByUserId(user.getId());

        // Deactivate the existing wallet
        if(existingWallet != null){
            existingWallet.setActive(false);
            walletRepository.saveOrUpdate(existingWallet);
        }

        Wallet newWallet = new Wallet();
        newWallet.setActive(true);
        newWallet.setAmount(0);
        newWallet.setUserId(user.getId());
        walletRepository.saveOrUpdate(newWallet);
    }

    public Integer fetchBalance(String username){
        User existingUser = userRepository.getUserByUsername(username);
        if(existingUser == null) throw new UserDoesNotExistException("User with name - " + username
                + " does not exist. Fetch Balance Activity Failed");

        Wallet existingWallet = walletRepository.getActiveWalletByUserId(existingUser.getId());
        if(existingWallet == null) throw new NoActiveWalletException("No Active wallet exists for user - "  + existingUser.getUsername());

        return existingWallet.getAmount();
    }
}
