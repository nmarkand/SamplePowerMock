package service;

public class TransactionUtils {
    public static boolean adjustBalance(int sourceBalance, int destinationBalance, int transactionAmount) {
        int destinationBalanceAfter = destinationBalance + transactionAmount;
        int sourceBalanceAfter = sourceBalance - transactionAmount;
        return transactionAmount == destinationBalanceAfter - destinationBalance ? true : false;
    }
}
