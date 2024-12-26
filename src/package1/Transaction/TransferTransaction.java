package package1.Transaction;

import package1.Bank.*;
public class TransferTransaction extends Transaction{

    // attribute
    private static String transactionType = "TransferTransaction";
    private int senderAccount;
    private int recipientAccount;

    // getters and setters
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        TransferTransaction.transactionType = transactionType;
    }

    public int getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(int senderAccount) {
        this.senderAccount = senderAccount;
    }

    public int getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(int recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    //implement method
    public static void addTransactionToBank(String name,double amount, int senderAccount, int recipientAccount){
        TransferTransaction transaction = new TransferTransaction();
        transaction.setClientName(name);
        transaction.setAmount(amount);
        transaction.setSenderAccount(senderAccount);
        transaction.setRecipientAccount(recipientAccount);
        Bank.transactions.add(transaction);

    }
}
