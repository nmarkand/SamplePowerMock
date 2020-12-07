package service;

public class LocalServiceImpl implements LocalService {

    @Override
    public boolean executeTransactions(int transactionAmount) {
        Integer sourceBalance = getSourceAccountBalance();
        Integer destinationBalance = getDestinationAccountBalance();
        return TransactionUtils.adjustBalance(sourceBalance, destinationBalance, transactionAmount);
    }

    @Override
    public boolean storeTransaction() {
        LocalDbService localDbService = new LocalDbService();
        return localDbService.saveTransaction(1010101);
    }

    private Integer getDestinationAccountBalance() {
        return 500;
    }

    private Integer getSourceAccountBalance() {
        return 500;
    }
}
