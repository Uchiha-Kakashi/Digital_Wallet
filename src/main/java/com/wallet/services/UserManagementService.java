package main.java.com.wallet.services;

import main.java.com.wallet.entities.User;
import main.java.com.wallet.exceptions.UsernameAlreadyExistsException;
import main.java.com.wallet.repositories.UserRepository;

public class UserManagementService {

    private static UserManagementService service = null;
    private UserRepository userRepository;
    private WalletManagementService walletManagementService;

    public UserManagementService() {
        this.userRepository = new UserRepository();
        this.walletManagementService = new WalletManagementService();
    }

    public static UserManagementService getInstance(){
        if(service == null) service = new UserManagementService();
        return service;
    }
    public void registerUser(String username){
        // Check if user exists with same name
        User existingUser = userRepository.getUserByUsername(username);
        if(existingUser != null) throw new UsernameAlreadyExistsException("User with name - " + username + " already Exists. Please use different username");

        User newUser = new User(username);
        Integer userId = userRepository.saveOrUpdate(newUser);
        walletManagementService.createNewWallet(newUser);

        System.out.println("Welcome to Flipkart Wallet - " + username);
        System.out.println("Your wallet has been initialized with 0 balance");
    }
}
