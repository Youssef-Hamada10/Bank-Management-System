package package1.User;

import java.util.Scanner;
import java.util.InputMismatchException;
import package1.Account.*;
import package1.Bank.*;

public class Client extends User{

    // object from Scanner class
    Scanner input = new Scanner(System.in);

    // attributes
    private String createdBy;
    private int ID;

    // constructors
    public Client() {
        ID = User.clicount;
        User.clicount++;
    }

    // getters and setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // implement methods
    public void editPersonalInfo() {
        System.out.println("------------");
        boolean valid = false;
        do {
            try {
                System.out.println("Press [1] to edit password");
                System.out.println("Press [2] to edit address");
                System.out.println("Press [3] to edit telephone");
                System.out.println("Press [4] to exit");
                int number = input.nextInt();
                input.nextLine();
                switch (number){
                    case 1:
                        System.out.println("Enter the new password : ");
                        setPassword(input.nextLine());
                        System.out.println("Password is changed");
                        valid = true;
                        break;
                    case 2:
                        System.out.println("Enter the new address : ");
                        setAddress(input.nextLine());
                        System.out.println("Address is changed");
                        valid = true;
                        break;
                    case 3:
                        System.out.println("Enter the new telephone number : ");
                        this.setTelephoneNumber(input.nextLine());
                        System.out.println("Telephone number is changed");
                        valid = true;
                        break;
                    case 4:
                        valid = true;
                        return;
                    default:
                        System.out.println("Invalid input.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
    }

    public void displayDetails() {
        System.out.println("--------------");
        System.out.printf("Client ID: %d \n",this.getID());
        System.out.println("Name: " + this.getFirstName() + " " + this.getLastName());
        System.out.printf("Address: %s \n",this.getAddress());
        System.out.printf("Telephone Number: %s \n",this.getTelephoneNumber());
        int NOA = 0; // number of account
        for (Account account : Bank.accounts){
            if (account.getClientName().equals(this.getFirstName().concat(" "  + this.getLastName()))){
                System.out.printf("Account number: %-8d Status: %-10s Type: %-20s Balance: %f \t Has credit card: %-10b",account.getAccountNumber(),account.getAccountStatus(),account.getAccountType(),account.getBalance(),account.getHasCreditCard());
                if(account.getHasCreditCard()){
                    System.out.printf("Creation Date: %-20s Expiration Date: %-20s Loyalty Points: %-10d \n",account.creditCard.getDate(),account.creditCard.getExpDate(),account.creditCard.getLoyaltyPoints());
                } else {
                    System.out.println("\n");
                }
                NOA++;
            }
        }
        if (NOA == 0){
            System.out.println("You don't have any account yet");
        }
    }

    int indexOfAccount;   // to select an account
    public void showAllAccount(){
        boolean found = false;
        do {
            try {
                for (int i = 0; i < Bank.accounts.size(); i++) {
                    if (Bank.accounts.get(i).getClientName().equals(this.getFirstName().concat(" "  + this.getLastName()))){
                        System.out.printf("[%d]\t\tAccount number: %-10d Status: %-10s Balance: %-10f \n",(i + 1),Bank.accounts.get(i).getAccountNumber(),Bank.accounts.get(i).getAccountStatus(),Bank.accounts.get(i).getBalance());
                    }
                }
                System.out.println("Enter number of the account");
                indexOfAccount = (input.nextInt() - 1);
                input.nextLine();
                found = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid input.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!found);
    }

    public void clientPage(){
        System.out.println("---------------");
        System.out.printf("Welcome, %s %s \n",this.getFirstName(),this.getLastName());
        boolean valid = false;
        while (true){
            try {
                System.out.println("---------------");
                System.out.println("Press [1] to edit personal information");
                System.out.println("Press [2] to display the details of the account");
                System.out.println("Press [3] to transfer money to another account");
                System.out.println("Press [4] to make a deposit");
                System.out.println("Press [5] to withDraw");
                System.out.println("Press [6] to show transaction history");
                System.out.println("Press [7] to ask for credit card");
                System.out.println("Press [8] to pay with credit card");
                System.out.println("Press [9] to disable the credit card");
                System.out.println("Press [10] to exchange loyalty points");
                System.out.println("Press [11] to apply intereast");
                System.out.println("Press [12] to logout");
                int choice = input.nextInt();
                input.nextLine();
                switch (choice){
                    case 1:
                        editPersonalInfo();
                        break;
                    case 2:
                        displayDetails();
                        break;
                    case 3:
                        showAllAccount();
                        if (Bank.accounts.get(indexOfAccount).getAccountStatus().equals("closed")){
                            System.out.println("This account is closed.");
                            return;
                        } else {
                            Bank.accounts.get(indexOfAccount).transferMoney();
                        }
                        break;
                    case 4:
                        showAllAccount();
                        if (Bank.accounts.get(indexOfAccount).getAccountStatus().equals("closed")){
                            System.out.println("This account is closed.");
                            return;
                        } else {
                            Bank.accounts.get(indexOfAccount).makeDeposit();
                        }
                        break;
                    case 5:
                        showAllAccount();
                        if (Bank.accounts.get(indexOfAccount).getAccountStatus().equals("closed")){
                            System.out.println("This account is closed.");
                            return;
                        } else {
                            Bank.accounts.get(indexOfAccount).withDraw();
                        }
                        break;
                    case 6:
                        showAllAccount();
                        Bank.accounts.get(indexOfAccount).showTransactionHistory();
                        break;
                    case 7:
                        showAllAccount();
                        if (Bank.accounts.get(indexOfAccount).getAccountStatus().equals("closed")){
                            System.out.println("This account is closed.");
                            return;
                        } else {
                            Bank.accounts.get(indexOfAccount).askForCreditCard();
                        }
                        break;
                    case 8:
                        showAllAccount();
                        if (Bank.accounts.get(indexOfAccount).getAccountStatus().equals("closed")){
                            System.out.println("This account is closed.");
                            return;
                        } else {
                            Bank.accounts.get(indexOfAccount).payWithCreditCard();
                        }
                        break;
                    case 9:
                        showAllAccount();
                        if (Bank.accounts.get(indexOfAccount).getAccountStatus().equals("closed")){
                            System.out.println("This account is closed.");
                            return;
                        } else {
                            Bank.accounts.get(indexOfAccount).disableCreditCard();
                        }
                        break;
                    case 10:
                        showAllAccount();
                        if (Bank.accounts.get(indexOfAccount).getAccountStatus().equals("closed")){
                            System.out.println("This account is closed.");
                            return;
                        } else {
                            Bank.accounts.get(indexOfAccount).exchangeLoyaltyPoints();
                        }
                        break;
                    case 11:
                        showAllAccount();
                        if (Bank.accounts.get(indexOfAccount).getAccountType().equals("saving account") && Bank.accounts.get(indexOfAccount).getAccountStatus().equals("active")){
                            ((SavingAccount) Bank.accounts.get(indexOfAccount)).applyInterest();
                        } else if (Bank.accounts.get(indexOfAccount).getAccountStatus().equals("closed")){
                            System.out.println("This account is closed");
                        } else {
                            System.out.println("This account is not saving account");
                        }
                        break;
                    case 12:
                        return;
                    default :
                        System.out.println("Invalid input.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
