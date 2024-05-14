import main.java.com.wallet.services.TransactionManagementService;
import main.java.com.wallet.services.UserManagementService;
import main.java.com.wallet.services.WalletManagementService;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        UserManagementService userRegistration = UserManagementService.getInstance();
        userRegistration.registerUser("Bob");
        userRegistration.registerUser("Alice");

        WalletManagementService walletService = WalletManagementService.getInstance();
        walletService.topupAmountToWallet("Bob", "UPI", 292);
        walletService.sendMoney("Bob", "Alice", 200);
        System.out.println("Balance of Bob is - " + walletService.fetchBalance("Bob"));
        System.out.println("Balance of Alice is - " + walletService.fetchBalance("Alice"));

        TransactionManagementService transactionService = TransactionManagementService.getInstance();
        transactionService.getAllUserTransactions("Bob");
        transactionService.getAllUserTransactions("Alice");
    }
}