package package1.Account;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import package1.Transaction.*;
import package1.CreditCard.*;
import package1.Bank.*;

public abstract class Account {

    // object from Scanner class
    Scanner input = new Scanner(System.in);

    //object from credit card
    public CreditCard creditCard = new CreditCard(this.getAccountNumber());

    // attributes
    private String clientName;
    private static int accountnum = 11111;
    private int accountNumber;
    private double balance;
    private String accountType;
    private String accountStatus;
    private boolean hasCreditCard;

    // constructor
    public Account(String clientName,double balance, String accountType) {
        this.accountNumber = Account.accountnum;
        Account.accountnum++;
        this.accountStatus = "active";
        this.clientName = clientName;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Account(){
        Account.accountnum++;
    }

    // getters and setters
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean getHasCreditCard() {
        return hasCreditCard;
    }

    public void setHasCreditCard(boolean hasCreditCard) {
        this.hasCreditCard = hasCreditCard;
    }

    // implement method
    public void transferMoney() {
        System.out.println("-------------");
        boolean isTransfered = false;
        boolean found = false;  // to check account number
        do {
            try {
                System.out.println("Enter the account number");
                int recipientAccount = input.nextInt();
                input.nextLine();
                int i = 0, j = 0;
                for (int k = 0; k < Bank.accounts.size(); k++) {
                    if (Bank.accounts.get(k).getAccountNumber() == recipientAccount) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("the recipient account not found ");
                } else {
                    boolean valid = false;
                    do {
                        try {
                            System.out.println("Enter amount to transfer :");
                            double amount = input.nextDouble();
                            input.nextLine();
                            valid = true;
                            if (this.getBalance() < amount) {
                                System.out.println("You don't hava enough money ");
                                return;
                            } else {
                                for (int accountnum = 0; accountnum < Bank.accounts.size(); accountnum++) {
                                    if (Bank.accounts.get(accountnum).getAccountNumber() == recipientAccount) {
                                        Bank.accounts.get(accountnum).setBalance(Bank.accounts.get(accountnum).getBalance() + amount);
                                        this.setBalance(this.getBalance() - amount);
                                        System.out.printf("The remaining amount is : %f \n", this.getBalance());
                                    }
                                }
                            }
                            // add to transaction list
                            TransferTransaction.addTransactionToBank((this.getClientName()),amount, this.getAccountNumber(), recipientAccount);
                            isTransfered = true;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input.");
                            input.nextLine(); // Clear the input buffer
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } while (!valid);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!isTransfered);
    }

    public void withDraw() {
        System.out.println("---------------");
        double amount = 0;
        boolean valid = false;
        do {
            try {
                System.out.println("Enter the amount");
                amount = input.nextDouble();
                input.nextLine();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        if (this.getBalance() < amount) {
            System.out.println("You don't have enough money");
            return;
        } else {
            this.setBalance(this.getBalance() - amount);
            System.out.printf("The remaining balance is : %f \n", this.getBalance());
        }
        // add to transaction list
        WithDrawTransaction.addTransactionToBank((this.getClientName()), amount, this.getAccountNumber());
    }

    public void makeDeposit() {
        System.out.println("---------------");
        double amount = 0;
        boolean valid = false;
        do {
            try {
                System.out.println("Enter the amount :");
                amount = input.nextDouble();
                input.nextLine();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
        this.setBalance(this.getBalance() + amount);
        System.out.printf("Your current balance is : %f \n",this.getBalance());
        // add to transaction list
        DepositTransaction.addTransactionToBank((this.getClientName()), amount, this.getAccountNumber());
    }

    public void payWithCreditCard() {
        System.out.println("-------------");
        double amount = 0;
        if(!this.getHasCreditCard()){
            System.out.println("You do not have a credit card");
        } else {
            if (this.creditCard.getStatus().equals("disable")) {
                System.out.println("Your card is disabled");
            } else {
                boolean valid = false;
                do {
                    try {
                        System.out.println("enter the amount due");
                        amount = input.nextDouble();
                        input.nextLine();
                        valid = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input.");
                        input.nextLine(); // Clear the input buffer
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } while (!valid);
                if (this.getBalance() >= amount) {
                    this.setBalance(this.getBalance() - amount);
                    System.out.printf("The remaining balance is : %f \n", this.getBalance());
                    this.creditCard.setLoyaltyPoints(this.creditCard.getLoyaltyPoints() + 5);    // handle loyalty points
                } else {
                    System.out.println("You don't have enough money");
                    return;
                }
                // add to transaction list
                CreditCardTransaction.addTransactionToBank((this.getClientName()), amount, this.getAccountNumber());
            }
        }
    }

    public void disableCreditCard() {
        System.out.println("---------------");
        this.creditCard.setStatus("disabled");
        this.setHasCreditCard(false);
        System.out.println("Your credit card is disabled");
    }

    public void exchangeLoyaltyPoints(){
        System.out.println("----------------");
        if (this.creditCard.getLoyaltyPoints() == 0){
            System.out.println("You don not have loyalty point");
        } else {
            System.out.printf("Your loyalty points is : %d \n",this.creditCard.getLoyaltyPoints());
            int amount = this.creditCard.getLoyaltyPoints() * 2;
            System.out.printf("Your balance will increase by : %d \n",amount);
            this.setBalance(this.getBalance() + amount);
            this.creditCard.setLoyaltyPoints(0);
        }
    }

    public void askForCreditCard() {
        System.out.println("--------------");
        if(this.getBalance() >= 20000){
            if (this.getHasCreditCard()){
                System.out.println("You already have one");
            } else {
                this.setHasCreditCard(true);
                this.creditCard.setStatus("active");
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                this.creditCard.setDate(now.format(formatter));
                this.creditCard.setExpDate( now.plusYears(4).format(formatter));
                System.out.println("You now have a credit card");
            }
        } else {
            System.out.println("You do not have enough money.");
        }

    }

    public void showTransactionHistory() {
        System.out.println("---------------");
        int counter = 0;
        boolean valid = false;
        for (int i = 0; i < Bank.transactions.size(); i++) {
            if (Bank.transactions.get(i) instanceof WithDrawTransaction) {
                if (((WithDrawTransaction) Bank.transactions.get(i)).getAccountNumber() == this.getAccountNumber()) {
                    valid = true;
                }
            } else if (Bank.transactions.get(i) instanceof DepositTransaction) {
                if (((DepositTransaction) Bank.transactions.get(i)).getAccountNumber() == this.getAccountNumber()) {
                    valid = true;
                }
            } else if (Bank.transactions.get(i) instanceof TransferTransaction) {
                if (((TransferTransaction) Bank.transactions.get(i)).getSenderAccount() == this.getAccountNumber() || ((TransferTransaction) Bank.transactions.get(i)).getRecipientAccount() == this.getAccountNumber()) {
                    valid = true;
                }
            } else if (Bank.transactions.get(i) instanceof CreditCardTransaction) {
                if (((CreditCardTransaction) Bank.transactions.get(i)).getAccountNumber() == this.getAccountNumber()) {
                    valid = true;
                }
            } else {
                System.out.println("Error......");
            }
            if (valid) {
                System.out.printf("Transaction id: %-8d", Bank.transactions.get(i).getId());
                System.out.printf("Date: %-20s", Bank.transactions.get(i).getTransactionDate());
                System.out.printf("Amount: %-20f", Bank.transactions.get(i).getAmount());
                if (Bank.transactions.get(i) instanceof WithDrawTransaction) {
                    System.out.printf("Transaction type: %-25s", ((WithDrawTransaction) Bank.transactions.get(i)).getTransactionType());
                    System.out.printf("Account number: %-10d \n", ((WithDrawTransaction) Bank.transactions.get(i)).getAccountNumber());
                } else if (Bank.transactions.get(i) instanceof TransferTransaction) {
                    System.out.printf("Transaction type: %-25s", ((TransferTransaction) Bank.transactions.get(i)).getTransactionType());
                    System.out.printf("Sender Account number: %-10d", ((TransferTransaction) Bank.transactions.get(i)).getSenderAccount());
                    System.out.printf("Recipient Account number: %-10d \n", ((TransferTransaction) Bank.transactions.get(i)).getRecipientAccount());
                } else if (Bank.transactions.get(i) instanceof DepositTransaction) {
                    System.out.printf("Transaction type: %-25s", ((DepositTransaction) Bank.transactions.get(i)).getTransactionType());
                    System.out.printf("Account number: %-10d \n", ((DepositTransaction) Bank.transactions.get(i)).getAccountNumber());
                } else {
                    System.out.printf("Transaction type: %-25s \n", ((CreditCardTransaction) Bank.transactions.get(i)).getTransactionType());
                }
                counter++;
            }
        }
        if (counter == 0){
            System.out.println("There is no transaction yet");
        }
    }

}
