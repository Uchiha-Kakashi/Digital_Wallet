import main.java.com.wallet.services.TransactionManagementService;
import main.java.com.wallet.services.UserManagementService;
import main.java.com.wallet.services.WalletManagementService;

public class Main {
    public static void main(String[] args) {
        UserManagementService userRegistration = UserManagementService.getInstance();
        userRegistration.registerUser("Bob");
        System.out.println("\n");
        userRegistration.registerUser("Alice");
        System.out.println("\n");
        userRegistration.registerUser("John");
        System.out.println("\n");

        WalletManagementService walletService = WalletManagementService.getInstance();
        walletService.topupAmountToWallet("Bob", "UPI", 300);
        System.out.println("\n");
        walletService.topupAmountToWallet("Alice", "CREDIT_CARD", 500);
        System.out.println("\n");
        walletService.topupAmountToWallet("John", "DEBIT_CARD", 400);
        System.out.println("\n");

        walletService.sendMoney("Bob", "Alice", 35);
        walletService.sendMoney("Alice", "John", 20);
        walletService.sendMoney("John", "Bob", 83);
        walletService.sendMoney("Bob", "John", 42);
        walletService.sendMoney("John", "Alice", 64);

        System.out.println("Balance of Bob is - " + walletService.fetchBalance("Bob"));
        System.out.println("\n");
        System.out.println("Balance of Alice is - " + walletService.fetchBalance("Alice"));
        System.out.println("\n");
        System.out.println("Balance of John is - " + walletService.fetchBalance("John"));
        System.out.println("\n");

        TransactionManagementService transactionService = TransactionManagementService.getInstance();
        transactionService.getUserTransactions("Bob", "amount", "sent");
        System.out.println("\n");
        transactionService.getUserTransactions("Alice", "time", "received");
        System.out.println("\n");
        transactionService.getUserTransactions("John", "time");
    }
}