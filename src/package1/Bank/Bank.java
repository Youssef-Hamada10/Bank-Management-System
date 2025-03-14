package package1.Bank;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import package1.User.*;
import package1.Account.*;
import package1.Transaction.*;

public class Bank {
    Scanner input = new Scanner(System.in);

    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Employee> employees = new ArrayList<>();
    public static ArrayList<Account> accounts = new ArrayList<>();
    public static ArrayList<Transaction> transactions = new ArrayList<>();

    // files path
    String EmployeePath = "src/files/Employee.csv";
    String ClientPath = "src/files/Client.csv";
    String TransactionPath = "src/files/Transaction.csv";
    String AccountPath = "src/files/Account.csv";



    public Bank() {
        System.out.println("\t\t\tBANK MANAGEMENT SYSTEM");

        readFromFile(EmployeePath, "employee");
        readFromFile(ClientPath, "client");
        readFromFile(AccountPath, "account");
        readFromFile(TransactionPath, "transaction");

        logIn();

        writeToFile(EmployeePath,"employee");
        writeToFile(ClientPath,"client");
        writeToFile(TransactionPath,"transaction");
        writeToFile(AccountPath,"account");
    }

    public void logIn() {
        while (true) {
            try {
                System.out.println("---------------");
                System.out.println("Press [1] to Login");
                System.out.println("Press [2] to Exit");

                int num = input.nextInt();
                input.nextLine(); // Consume the newline character

                if (num == 1) {
                    System.out.println("Enter Your username:");
                    String username = input.nextLine();
                    System.out.println("Enter your password:");
                    String password = input.nextLine();

                    boolean valid = false;

                    if (username.equals("admin") && password.equals("admin")) {
                        valid = true;
                    }

                    if (valid) {
                        Admin.adminPage();
                    } else {
                        int emp = 0;
                        for (; emp < Bank.employees.size(); emp++) {
                            if (Bank.employees.get(emp).getUsername().equals(username) && Bank.employees.get(emp).getPassword().equals(password)) {
                                valid = true;
                                break;
                            }
                        }

                        if (valid) {
                            Bank.employees.get(emp).employeePage();
                        } else {
                            int cli = 0;
                            for (; cli < Bank.clients.size(); cli++) {
                                if (Bank.clients.get(cli).getUsername().equals(username) && Bank.clients.get(cli).getPassword().equals(password)) {
                                    valid = true;
                                    break;
                                }
                            }

                            if (valid) {
                                Bank.clients.get(cli).clientPage();
                            } else {
                                System.out.println("Invalid username or password");
                            }
                        }
                    }
                } else if (num == 2) {  // for exit choice
                    break;
                } else {
                    System.out.println("Invalid choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear input
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void writeToFile(String fileName, String type){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            if (type.equals("employee")){
                bw.write("ID,FirstName,LastName,Username,Password,Address,Position,TelephoneNumber,GraduatedCollege,TotalGrade,YearOfGraduation");
                bw.newLine();
                for (Employee emp : Bank.employees){
                    bw.write(emp.getID() + "," + emp.getFirstName() + "," + emp.getLastName() + "," +
                            emp.getUsername() + "," + emp.getPassword() + "," + emp.getAddress() + "," + emp.getPosition() + "," +
                            emp.getTelephoneNumber() + "," + emp.getGraduatedCollege() + "," + emp.getTotalGrade() + "," + emp.getYearOfGraduation());
                    bw.newLine();
                }
            } else if (type.equals("client")) {
                bw.write("ID,FirstName,LastName,UserName,Password,Address,TelephoneNumber,CreatedBy");
                bw.newLine();
                for (Client cli : Bank.clients){
                    bw.write(cli.getID() + "," + cli.getFirstName() + "," + cli.getLastName() + "," +
                            cli.getUsername() + "," + cli.getPassword() + "," + cli.getAddress() + "," +
                            cli.getTelephoneNumber() + "," + cli.getCreatedBy());
                    bw.newLine();
                }
            } else if (type.equals("account")) {
                bw.write("Name,AccountNumber,Status,Type,Balance,HasCreditCard,CreationDate,ExpirationDate,LoyaltyPoint");
                bw.newLine();
                for (Account acc : Bank.accounts){
                    bw.write(acc.getClientName() + "," + acc.getAccountNumber() + "," + acc.getAccountStatus() + "," +
                            acc.getAccountType() + "," + acc.getBalance() + "," + acc.getHasCreditCard() + "," +
                            acc.creditCard.getDate() + "," + acc.creditCard.getExpDate() + "," + acc.creditCard.getLoyaltyPoints());
                    bw.newLine();
                }
            } else if (type.equals("transaction")){
                bw.write("Type,ID,Name,Date,Amount,AccountNumber,RecipientAccount");
                bw.newLine();
                for (Transaction trans : Bank.transactions){
                    if (trans instanceof TransferTransaction){
                        bw.write("transferTransaction" + "," + trans.getId() + "," + trans.getClientName() + "," +
                                trans.getTransactionDate() + "," + trans.getAmount() + "," + ((TransferTransaction)trans).getSenderAccount() + "," +
                                ((TransferTransaction)trans).getRecipientAccount() + ",");
                        bw.newLine();
                    } else if (trans instanceof WithDrawTransaction) {
                        bw.write("withdrawTransaction" + "," +
                                trans.getId() + "," + trans.getClientName() + "," + trans.getTransactionDate() + "," +
                                trans.getAmount() + "," + ((WithDrawTransaction)trans).getAccountNumber() +"," );
                        bw.newLine();
                    } else if (trans instanceof DepositTransaction) {
                        bw.write("depositTransaction" + "," +
                                trans.getId() + "," + trans.getClientName() + "," + trans.getTransactionDate() + "," +
                                trans.getAmount() + "," + ((DepositTransaction)trans).getAccountNumber() +"," );
                        bw.newLine();
                    } else {
                        bw.write("creditCardTransaction" + "," +
                                trans.getId() + "," + trans.getClientName() + "," + trans.getTransactionDate() + "," +
                                trans.getAmount() + "," + ((CreditCardTransaction)trans).getAccountNumber() +"," );
                        bw.newLine();
                    }
                }
            }
            bw.close();
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void readFromFile(String fileName, String type){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();  // Skip the header line
            if (type.equals("employee")){
                while ((line = br.readLine()) != null){
                    String[] values = line.split(",");
                    Employee emp = new Employee();
                    emp.setID(Integer.parseInt(values[0]));
                    emp.setFirstName(values[1]);
                    emp.setLastName(values[2]);
                    emp.setUsername(values[3]);
                    emp.setPassword(values[4]);
                    emp.setAddress(values[5]);
                    emp.setPosition(values[6]);
                    emp.setTelephoneNumber(values[7]);
                    emp.setGraduatedCollege(values[8]);
                    emp.setTotalGrade(values[9]);
                    emp.setYearOfGraduation(Integer.parseInt(values[10]));
                    Bank.employees.add(emp);
                }
            } else if (type.equals("client")) {
                while ((line = br.readLine()) != null){
                    String[] values = line.split(",");
                    Client cli = new Client();
                    cli.setID(Integer.parseInt(values[0]));
                    cli.setFirstName(values[1]);
                    cli.setLastName(values[2]);
                    cli.setUsername(values[3]);
                    cli.setPassword(values[4]);
                    cli.setAddress(values[5]);
                    cli.setTelephoneNumber(values[6]);
                    cli.setCreatedBy(values[7]);
                    Bank.clients.add(cli);
                }
            } else if (type.equals("account")) {
                while ((line = br.readLine()) != null){
                    String[] values = line.split(",");
                    if(values[3].equals("saving account")){
                        SavingAccount acc = new SavingAccount();
                        acc.setClientName(values[0]);
                        acc.setAccountNumber(Integer.parseInt(values[1]));
                        acc.setAccountStatus(values[2]);
                        acc.setAccountType(values[3]);
                        acc.setBalance(Double.parseDouble(values[4]));
                        acc.setHasCreditCard(Boolean.parseBoolean(values[5]));
                        if(acc.getHasCreditCard()){
                            acc.creditCard.setDate(values[6]);
                            acc.creditCard.setExpDate(values[7]);
                            acc.creditCard.setLoyaltyPoints(Integer.parseInt(values[8]));
                        }
                        Bank.accounts.add(acc);
                    } else {
                        CurrentAccount acc = new CurrentAccount();
                        acc.setClientName(values[0]);
                        acc.setAccountNumber(Integer.parseInt(values[1]));
                        acc.setAccountStatus(values[2]);
                        acc.setAccountType(values[3]);
                        acc.setBalance(Double.parseDouble(values[4]));
                        acc.setHasCreditCard(Boolean.parseBoolean(values[5]));
                        if(acc.getHasCreditCard()){
                            acc.creditCard.setDate(values[6]);
                            acc.creditCard.setExpDate(values[7]);
                            acc.creditCard.setLoyaltyPoints(Integer.parseInt(values[8]));
                        }
                        Bank.accounts.add(acc);
                    }
                }
            } else if (type.equals("transaction")){
                while ((line = br.readLine()) != null){
                    String[] values = line.split(",");
                    if (values[0].equals("withDrawTransaction")){
                        WithDrawTransaction trans = new WithDrawTransaction();
                        trans.setTransactionType(values[0]);
                        trans.setId(Integer.parseInt(values[1]));
                        trans.setClientName(values[2]);
                        trans.setTransactionDate(values[3]);
                        trans.setAmount(Double.parseDouble(values[4]));
                        trans.setAccountNumber(Integer.parseInt(values[5]));
                        Bank.transactions.add(trans);
                    } else if (values[0].equals("transferTransaction")) {
                        TransferTransaction trans = new TransferTransaction();
                        trans.setTransactionType(values[0]);
                        trans.setId(Integer.parseInt(values[1]));
                        trans.setClientName(values[2]);
                        trans.setTransactionDate(values[3]);
                        trans.setAmount(Double.parseDouble(values[4]));
                        trans.setSenderAccount(Integer.parseInt(values[5]));
                        trans.setRecipientAccount(Integer.parseInt(values[6]));
                        Bank.transactions.add(trans);
                    } else if (values[0].equals("depositTransaction")) {
                        DepositTransaction trans = new DepositTransaction();
                        trans.setTransactionType(values[0]);
                        trans.setId(Integer.parseInt(values[1]));
                        trans.setClientName(values[2]);
                        trans.setTransactionDate(values[3]);
                        trans.setAmount(Double.parseDouble(values[4]));
                        trans.setAccountNumber(Integer.parseInt(values[5]));
                        Bank.transactions.add(trans);
                    } else {
                        CreditCardTransaction trans = new CreditCardTransaction();
                        trans.setTransactionType(values[0]);
                        trans.setId(Integer.parseInt(values[1]));
                        trans.setClientName(values[2]);
                        trans.setTransactionDate(values[3]);
                        trans.setAmount(Double.parseDouble(values[4]));
                        trans.setAccountNumber(Integer.parseInt(values[5]));
                        Bank.transactions.add(trans);
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
