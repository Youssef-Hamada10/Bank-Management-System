package package1.Transaction;

import package1.Bank.*;

public class DepositTransaction extends Transaction{

    //attribute
    private static String transactionType = "DepositTransaction";
    private int accountNumber;

    //getters and setters

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        DepositTransaction.transactionType = transactionType;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    //implement method
    public static void addTransactionToBank(String name, double amount, int accountNumber){
        DepositTransaction transaction = new DepositTransaction();
        transaction.setClientName(name);
        transaction.setAmount(amount);
        transaction.setAccountNumber(accountNumber);
        Bank.transactions.add(transaction);
    }
}
