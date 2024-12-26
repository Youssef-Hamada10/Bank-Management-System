package package1.User;

import java.util.Scanner;
import java.util.InputMismatchException;
import package1.Transaction.*;
import package1.Bank.*;

public class Admin {

    // object from Scanner class
    private static Scanner input = new Scanner(System.in);

    // attribute
    private final String username = "admin";
    private final String password = "admin";

    // getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // implement methods
    private static void addNewEmployee(){
        System.out.println("--------------");
        Employee emp = new Employee();
        System.out.println("Enter employee's first name :");
        emp.setFirstName(input.nextLine().trim());
        System.out.println("Enter employee's last name");
        emp.setLastName(input.nextLine().trim());
//        System.out.println("Enter employee's username :");
        emp.setUsername();
        System.out.println("Enter employee's password :");
        emp.setPassword(input.nextLine());
        System.out.println("Enter employee's telephone number :");
        emp.setTelephoneNumber(input.nextLine());
        System.out.println("Enter employee's address :");
        emp.setAddress(input.nextLine());
        System.out.println("Enter employee's position :");
        emp.setPosition(input.nextLine());
        System.out.println("Enter employee's graduated college :");
        emp.setGraduatedCollege(input.nextLine());

        boolean valid = false;   // for check graduation year
        do{
            try {
                System.out.println("Enter employee's year of graduation :");
                emp.setYearOfGraduation(input.nextInt());
                input.nextLine();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            }
        } while(!valid);

        System.out.println("Enter employee's total grade :");
        emp.setTotalGrade(input.nextLine());
        System.out.println("Employee added");
        Bank.employees.add(emp);
    }

    private static void displayAllEmployees(){
        System.out.println("--------------");
        try {
            if (Bank.employees.isEmpty()){
                System.out.println("there is no employees yet");
            } else {
                for(int i = 0; i < Bank.employees.size(); i++){
                    System.out.printf("[%d]\t ID: %-5d Name: %-15s Address: %-10s TelephoneNumber: %-17s Graduated from: %-12s year of graduation: %-8d Total grade: %-20s \n",
                            (i + 1),Bank.employees.get(i).getID(),Bank.employees.get(i).getFirstName().concat(" " + Bank.employees.get(i).getLastName()),
                            Bank.employees.get(i).getAddress(),Bank.employees.get(i).getTelephoneNumber(),Bank.employees.get(i).getGraduatedCollege(),
                            Bank.employees.get(i).getYearOfGraduation(),Bank.employees.get(i).getTotalGrade());
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void displayAllClients(){
        System.out.println("--------------");
        if (Bank.clients.isEmpty()){
            System.out.println("There is no clients yet");
        } else{
            try {
                for(int i = 0; i < Bank.clients.size(); i++){
                    int numOfAccount = 0;
                    System.out.printf("[%d]\t ID: %-5d Name: %-15s Address: %-10s TelephoneNumber: %-17s CreatedBy: %-20s \n",(i + 1),Bank.clients.get(i).getID(),
                            Bank.clients.get(i).getFirstName().concat(" " + Bank.clients.get(i).getLastName()),Bank.clients.get(i).getAddress(),
                            Bank.clients.get(i).getTelephoneNumber(),Bank.clients.get(i).getCreatedBy());
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private static void displayAllAccounts(){
        for (int i = 0; i < Bank.accounts.size(); i++) {
            System.out.printf("[%d]\t Client: %-15s Account number: %-10d Type: %-20s Status: %-10s Balance: %f \t HasCreditCard: %-10b",(i + 1),Bank.accounts.get(i).getClientName(),
                    Bank.accounts.get(i).getAccountNumber(),Bank.accounts.get(i).getAccountType(),Bank.accounts.get(i).getAccountStatus(),Bank.accounts.get(i).getBalance(),
                    Bank.accounts.get(i).getHasCreditCard());
            if(Bank.accounts.get(i).getHasCreditCard()){
                System.out.printf("Creation Date: %-20s Expiration Date: %-20s Loyalty Points: %-10d \n",Bank.accounts.get(i).creditCard.getDate(),Bank.accounts.get(i).creditCard.getExpDate(),Bank.accounts.get(i).creditCard.getLoyaltyPoints());
            } else {
                System.out.println("\n");
            }
        }
    }

    private static void displayAllTransaction(){
        System.out.println("--------------");
        if (Bank.transactions.isEmpty()){
            System.out.println("There is no transactions yet");
        } else {
            boolean valid = false;   // for check choice
            do{
                System.out.println("Press [1] to display by date :");
                System.out.println("Press [2] to display by client :");
                System.out.println("Press [3] to display by employee :");
                System.out.println("Press [4] to exit :");
                try {
                    int choice = input.nextInt();
                    input.nextLine();
                    switch (choice){
                        case 1:
                            System.out.println("Enter date at the form dd-MM-yyyy :");
                            String date = input.nextLine();
                            displayTransactionsByDate(date.trim());
                            valid = true;
                            break;
                        case 2:
                            System.out.println("Enter client name :");
                            String clientName = input.nextLine();
                            displayTransactionsByClient(clientName.trim().toLowerCase());
                            valid = true;
                            break;
                        case 3:
                            System.out.println("Enter employee name :");
                            String employeeName = input.nextLine().trim().toLowerCase();
                            displayTransactionsByEmployee(employeeName);
                            valid = true;
                            break;
                        case 4:
                            valid = true;
                            return;
                        default:
                            System.out.println("Invalid input.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    input.nextLine(); // Clear the input buffer
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while(!valid);
        }
    }

    private static void displayTransactionsByDate(String date){
        System.out.println("--------------");
        if(Bank.transactions.isEmpty()){
            System.out.println("There is no transaction yet");
        } else{
            try {
                int counter = 0;    // to know the transaction is exist or no
                for(int i = 0; i < Bank.transactions.size(); i++){
                    if(Bank.transactions.get(i).getTransactionDate().contains(date)){
                        System.out.printf("Transaction id: %-8d",Bank.transactions.get(i).getId());
                        System.out.printf("Date: %-18s",Bank.transactions.get(i).getTransactionDate());
                        System.out.printf("Amount: %f \t",Bank.transactions.get(i).getAmount());
                        if(Bank.transactions.get(i) instanceof WithDrawTransaction){
                            System.out.printf("Transaction type: %-25s",((WithDrawTransaction) Bank.transactions.get(i)).getTransactionType());
                            System.out.printf("Account number: %-10d \n",((WithDrawTransaction) Bank.transactions.get(i)).getAccountNumber());
                        } else if (Bank.transactions.get(i) instanceof TransferTransaction) {
                            System.out.printf("Transaction type: %-25s",((TransferTransaction) Bank.transactions.get(i)).getTransactionType());
                            System.out.printf("Sender Account number: %-10d",((TransferTransaction) Bank.transactions.get(i)).getSenderAccount());
                            System.out.printf("Recipient Account number: %-10d \n",((TransferTransaction) Bank.transactions.get(i)).getRecipientAccount());
                        } else if (Bank.transactions.get(i) instanceof DepositTransaction) {
                            System.out.printf("Transaction type: %-25s",((DepositTransaction) Bank.transactions.get(i)).getTransactionType());
                            System.out.printf("Account number: %-10d \n",((DepositTransaction) Bank.transactions.get(i)).getAccountNumber());
                        } else {
                            System.out.printf("Transaction type: %-25s ",((CreditCardTransaction) Bank.transactions.get(i)).getTransactionType());
                            System.out.printf("Account number: %-10d \n",((CreditCardTransaction) Bank.transactions.get(i)).getAccountNumber());
                        }
                        counter++;
                    }
                }
                if(counter == 0){
                    System.out.println("There is no transaction with this date " + date);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (IndexOutOfBoundsException e){
                System.out.println("Invalid input.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void displayTransactionsByClient(String clientName){
        System.out.println("--------------");
        if(Bank.clients.isEmpty()){
            System.out.println("There is no clients yet");
        } else if (Bank.transactions.isEmpty()) {
            System.out.println("There is no transaction yet");
        } else {
            try {
                int counter = 0;
                for(int i = 0; i < Bank.transactions.size(); i++){
                    if(Bank.transactions.get(i).getClientName().equals(clientName)){
                        System.out.printf("Transaction id: %-6d",Bank.transactions.get(i).getId());
                        System.out.printf("Date: %-20s",Bank.transactions.get(i).getTransactionDate());
                        System.out.printf("Amount: %f \t",Bank.transactions.get(i).getAmount());
                        if(Bank.transactions.get(i) instanceof WithDrawTransaction){
                            System.out.printf("Transaction type: %-25s",((WithDrawTransaction) Bank.transactions.get(i)).getTransactionType());
                            System.out.printf("Account number: %-10d \n",((WithDrawTransaction) Bank.transactions.get(i)).getAccountNumber());
                        } else if (Bank.transactions.get(i) instanceof TransferTransaction) {
                            System.out.printf("Transaction type: %-25s",((TransferTransaction) Bank.transactions.get(i)).getTransactionType());
                            System.out.printf("Sender Account number: %-10d",((TransferTransaction) Bank.transactions.get(i)).getSenderAccount());
                            System.out.printf("Recipient Account number: %-10d \n",((TransferTransaction) Bank.transactions.get(i)).getRecipientAccount());
                        } else if (Bank.transactions.get(i) instanceof DepositTransaction) {
                            System.out.printf("Transaction type: %-25s",((DepositTransaction) Bank.transactions.get(i)).getTransactionType());
                            System.out.printf("Account number: %-10d \n",((DepositTransaction) Bank.transactions.get(i)).getAccountNumber());
                        } else {
                            System.out.printf("Transaction type: %-25s",((CreditCardTransaction) Bank.transactions.get(i)).getTransactionType());
                            System.out.printf("Account Number: %-25s \n",((CreditCardTransaction) Bank.transactions.get(i)).getAccountNumber());
                        }
                        counter++;
                    }
                }
                if(counter == 0){
                    System.out.println("There is no transaction with this client " + clientName);
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (IndexOutOfBoundsException e){
                System.out.println("Invalid input.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static void displayTransactionsByEmployee(String employeeName){
        System.out.println("--------------");
        if(Bank.employees.isEmpty()){
            System.out.println("There is no employees yet");
        } else if (Bank.transactions.isEmpty()) {
            System.out.println("There is no transactions yet");
        }else if (Bank.clients.isEmpty()) {
            System.out.println("There is no clients yet");
        } else {
            String clientName = " ";     // to select the client
            try {
                int counter = 0;
                for (int k = 0; k < Bank.clients.size(); k++){
                    if (Bank.clients.get(k).getCreatedBy().equals(employeeName)){
                        clientName = Bank.clients.get(k).getFirstName().concat(" " + Bank.clients.get(k).getLastName());
                        for(int i = 0; i < Bank.transactions.size(); i++){
                            if(Bank.transactions.get(i).getClientName().equals(clientName)){
                                System.out.printf("Transaction id: %-6d",Bank.transactions.get(i).getId());
                                System.out.printf("Date: %-20s",Bank.transactions.get(i).getTransactionDate());
                                System.out.printf("Amount: %f \t",Bank.transactions.get(i).getAmount());
                                if(Bank.transactions.get(i) instanceof WithDrawTransaction){
                                    System.out.printf("Transaction type: %-25s",((WithDrawTransaction) Bank.transactions.get(i)).getTransactionType());
                                    System.out.printf("Account number: %-10d \n",((WithDrawTransaction) Bank.transactions.get(i)).getAccountNumber());
                                } else if (Bank.transactions.get(i) instanceof TransferTransaction) {
                                    System.out.printf("Transaction type: %-25s",((TransferTransaction) Bank.transactions.get(i)).getTransactionType());
                                    System.out.printf("Sender Account number: %-10d",((TransferTransaction) Bank.transactions.get(i)).getSenderAccount());
                                    System.out.printf("Recipient Account number: %-10d \n",((TransferTransaction) Bank.transactions.get(i)).getRecipientAccount());
                                } else if (Bank.transactions.get(i) instanceof DepositTransaction) {
                                    System.out.printf("Transaction type: %-25s",((DepositTransaction) Bank.transactions.get(i)).getTransactionType());
                                    System.out.printf("Account number: %-10d \n",((DepositTransaction) Bank.transactions.get(i)).getAccountNumber());
                                } else {
                                    System.out.printf("Transaction type: %-25s",((CreditCardTransaction) Bank.transactions.get(i)).getTransactionType());
                                    System.out.printf("Account Number: %-25s \n",((CreditCardTransaction) Bank.transactions.get(i)).getAccountNumber());
                                }
                                counter++;
                            }
                        }
                    }
                }
                if(counter == 0){
                    System.out.println("There is no transaction with this employee " + employeeName);
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (IndexOutOfBoundsException e){
                System.out.println("Invalid input.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void adminPage() {
        System.out.println("---------------");
        System.out.println("Welcome, ADMIN");
        while (true) {
            try {
                System.out.println("---------------");
                System.out.println("Press [1] to add new employees :");
                System.out.println("Press [2] to display all employees :");
                System.out.println("Press [3] to display all clients :");
                System.out.println("Press [4] to display all accounts :");
                System.out.println("Press [5] to display all transactions :");
                System.out.println("Press [6] to logout :");
                int choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1:
                        addNewEmployee();
                        break;
                    case 2:
                        displayAllEmployees();
                        break;
                    case 3:
                        displayAllClients();
                        break;
                    case 4:
                        displayAllAccounts();
                        break;
                    case 5:
                        displayAllTransaction();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid input.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear input
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
