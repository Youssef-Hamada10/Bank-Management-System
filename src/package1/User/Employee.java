package package1.User;

import java.util.Scanner;
import java.util.InputMismatchException;
import package1.Account.*;
import package1.Bank.*;

public class Employee extends User{

    // object from scanner class
    Scanner input = new Scanner(System.in);

    // attributes
    private int ID;
    private String position;
    private String graduatedCollege;
    private int yearOfGraduation;
    private String totalGrade;

    public Employee() {
        ID = User.empcount;
        User.empcount++;
    }

    // getters and setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGraduatedCollege() {
        return graduatedCollege;
    }

    public void setGraduatedCollege(String graduatedCollege) {
        this.graduatedCollege = graduatedCollege;
    }

    public int getYearOfGraduation() {
        return yearOfGraduation;
    }

    public void setYearOfGraduation(int yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    public String getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(String totalGrade) {
        this.totalGrade = totalGrade;
    }

    // implement methods
    public void displayEmployeeInfo() {
        System.out.println("--------------");
        System.out.println("Name : " + this.getFirstName() + " " + this.getLastName());
        System.out.println("ID : " + this.getID());
        System.out.println("Username : " + this.getUsername());
        System.out.println("password : " + this.getPassword());
        System.out.println("Address : " + this.getAddress());
        System.out.println("Position : " + this.getPosition());
        System.out.println("Telephone number : " + this.getTelephoneNumber());
        System.out.println("graduated from : " + this.getGraduatedCollege() + " since : " + this.getYearOfGraduation());
        System.out.println("Total grade : " + this.getTotalGrade());
    }

    public void editPersonalInfo() {    // just address - position
        System.out.println("---------------");
        System.out.println("Your address is : " + getAddress());
        System.out.println("Your position is : " + getPosition());

        boolean valid = false;   // for check choice
        do {
            try {
                System.out.println("---------------");
                System.out.println("Press [1] to edit your address");
                System.out.println("press [2] to edit your position");
                System.out.println("Press [3] to exit");
                int choice = input.nextInt();
                input.nextLine();
                if (choice == 1) {
                    System.out.println("Enter the new address");
                    this.setAddress(input.nextLine());
                    valid = true;
                } else if (choice == 2) {
                    System.out.println("Enter the new position");
                    this.setPosition(input.nextLine());
                    valid = true;
                } else if (choice == 3) {
                    valid = true;
                    return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
    }

    public void createClient() {
        System.out.println("-----------");
        Client client = new Client();
        System.out.println("Enter client's first name :");
        client.setFirstName(input.nextLine().trim());
        System.out.println("Enter client's last name");
        client.setLastName(input.nextLine().trim());
        client.setUsername();
        System.out.println("Enter client's password :");
        client.setPassword(input.nextLine());
        System.out.println("Enter client's telephone number :");
        client.setTelephoneNumber(input.nextLine());
        System.out.println("Enter client's address :");
        client.setAddress(input.nextLine());
        client.setCreatedBy(this.getFirstName().concat(" " + this.getLastName()));
        Bank.clients.add(client);
        System.out.println("Client created");
    }

    public void createClientAccount() {
        System.out.println("---------------");
        if (Bank.clients.isEmpty()){
            System.out.println("There is no client yet");
            return;
        } else {
            for (int i = 0; i < Bank.clients.size(); i++) {
                System.out.printf("[%d]\t\tID: %-10dName: %s %s \n",(i + 1),Bank.clients.get(i).getID(),Bank.clients.get(i).getFirstName(),Bank.clients.get(i).getLastName());
            }
            boolean valid = false;   // for checking
            do {
                try {
                    System.out.println("---------------");
                    System.out.println("Enter number of client to create account");
                    int num = input.nextInt();
                    input.nextLine();
                    System.out.println("Enter account type");
                    String type = input.nextLine();
                    System.out.println("Enter balance");
                    double balance = input.nextDouble();
                    input.nextLine();
                    if (type.trim().equalsIgnoreCase("saving account")) {
                        SavingAccount account = new SavingAccount(Bank.clients.get(num - 1).getFirstName().concat(" " + Bank.clients.get(num - 1).getLastName()), balance);
                        Bank.accounts.add(account);
                        System.out.println("Account created");
                        valid = true;
                    } else if (type.trim().equalsIgnoreCase("current account")) {
                        CurrentAccount account = new CurrentAccount(Bank.clients.get(num - 1).getFirstName().concat(" " + Bank.clients.get(num - 1).getLastName()), balance);
                        Bank.accounts.add(account);
                        System.out.println("Account created");
                        valid = true;
                    } else {
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
    }

    public void editClientAccount() {
        System.out.println("-----------------");
        if (Bank.clients.isEmpty()) {
            System.out.println("There is no client yet");
            return;
        } else {
            int NOA;
            for (int numOfClient = 0; numOfClient < Bank.clients.size(); numOfClient++) {
                NOA = 0;
                System.out.printf("[%d]\t\tID: %-10d Name: %s %s\n", (numOfClient + 1), Bank.clients.get(numOfClient).getID(), Bank.clients.get(numOfClient).getFirstName(), Bank.clients.get(numOfClient).getLastName());
                System.out.println("\t\t----------");
                for (int numOfAccount = 0; numOfAccount < Bank.accounts.size(); numOfAccount++) {
                    if (Bank.accounts.get(numOfAccount).getClientName().equals(Bank.clients.get(numOfClient).getFirstName().concat(" " + Bank.clients.get(numOfClient).getLastName()))) {
                        System.out.printf("\t\t[%d]\t\tAccount number: %-10d Status: %-10s Type: %-25s Balance: %-10f \n", (numOfAccount + 1),
                                Bank.accounts.get(numOfAccount).getAccountNumber(), Bank.accounts.get(numOfAccount).getAccountStatus(),
                                Bank.accounts.get(numOfAccount).getAccountType(), Bank.accounts.get(numOfAccount).getBalance());
                        NOA++;
                    }
                }
                if (NOA == 0) {
                    System.out.println("\t\t This client has no accounts");
                }
                System.out.println("-----------------");
            }
            boolean valid = false;
            do {
                try {
                    System.out.println("Enter number of client");
                    int numOfClient = input.nextInt();
                    input.nextLine();
                    System.out.println("Enter number of account to edit :");
                    int numOfAccount = input.nextInt();
                    input.nextLine();

                    valid = true;
                    boolean innerValid = false;
                    do {
                        try {
                            System.out.println("Press [1] to edit account status");
                            System.out.println("Press [2] to edit account balance");
                            System.out.println("Press [3] to edit account type");
                            System.out.println("Press [4] to exit");
                            int num = input.nextInt();
                            input.nextLine();
                            innerValid = true;
                            switch (num) {
                                case 1:
                                    System.out.println("Enter the new account status");
                                    Bank.accounts.get(numOfAccount - 1).setAccountStatus(input.nextLine());
                                    break;
                                case 2:
                                    System.out.println("Enter the new balance");
                                    Bank.accounts.get(numOfAccount - 1).setBalance(input.nextDouble());
                                    input.nextLine();
                                    break;
                                case 3:
                                    System.out.println("Enter the new type");
                                    Bank.accounts.get(numOfAccount - 1).setAccountType(input.nextLine());
                                    break;
                                case 4:
                                    return;
                                default:
                                    System.out.println("Invalid input.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input.");
                            input.nextLine(); // Clear the input buffer
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Invalid input.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } while (!innerValid);
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    input.nextLine(); // Clear the input buffer
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid input.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (!valid);
        }
    }

    public void searchForClient() {
        boolean found = false;
        do {
            try {
                System.out.println("-------------");
                System.out.println("[1] Using client's name");
                System.out.println("[2] Using account number");
                int choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Enter client name :");
                        searchForClientByName(input.nextLine());
                        found = true;
                        break;
                    case 2:
                        System.out.println("Enter account number");
                        searchForClientByAccountNumber(input.nextInt());
                        found = true;
                        break;
                    default:
                        System.out.println("Invalid input.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!found);
    }

    public void searchForClientByName(String Name) {
        System.out.println("---------------");
        boolean found;
        boolean getclient = false;
        for (int i = 0; i < Bank.clients.size(); i++) {
            found = false;
            if (Bank.clients.get(i).getFirstName().concat(" " + Bank.clients.get(i).getLastName()).equalsIgnoreCase(Name.trim())) {
                getclient = true;
                found = true;
                if (found) {
                    System.out.println("The client is exist");
                    System.out.printf("Client name: %s %s \t Address: %s \t Telephone number: %s \n", Bank.clients.get(i).getFirstName(), Bank.clients.get(i).getLastName(),
                            Bank.clients.get(i).getAddress(), Bank.clients.get(i).getTelephoneNumber());
                    for (int j = 0; j < Bank.accounts.size(); j++) {
                        if (Bank.accounts.get(j).getClientName().equalsIgnoreCase(Name)){
                            System.out.printf("Account number: %d \t Status: %s \t Type: %s \t Balance: %f \n",Bank.accounts.get(j).getAccountNumber(),Bank.accounts.get(j).getAccountStatus(),Bank.accounts.get(j).getAccountType(),Bank.accounts.get(j).getBalance());
                        }
                    }
                }
            }
            if (!getclient) {
                System.out.println("No clients match your search");
            }
        }
    }

    public void searchForClientByAccountNumber(int accountNumber) {
        System.out.println("---------------");
        input.nextLine();
        boolean found = false;
        for (int i = 0; i < Bank.accounts.size(); i++) {
            if (Bank.accounts.get(i).getAccountNumber() == accountNumber){
                found = true;
                System.out.printf("Name: %s \t Status: %s \t Balance: %f \n",Bank.accounts.get(i).getClientName(),Bank.accounts.get(i).getAccountStatus(),Bank.accounts.get(i).getBalance());
                break;
            }
        }
        if (!found) {
            System.out.println("The client is not exist ");
        }
    }

    public void deleteClient() {
        boolean valid = false;
        do {
            try {
                System.out.println("-------------");
                if (Bank.clients.isEmpty()) {
                    System.out.println("There is no client yet");
                } else {
                    for (int numOfClient = 0; numOfClient < Bank.clients.size(); numOfClient++) {
                        System.out.printf("[%d]\t\tID: %-10d Name: %s %s \n",(numOfClient + 1),Bank.clients.get(numOfClient).getID(),Bank.clients.get(numOfClient).getFirstName(),Bank.clients.get(numOfClient).getLastName());
                    }
                    System.out.println("Enter number of client to delete :");
                    int numOfClient = input.nextInt();
                    input.nextLine();
                    Bank.clients.remove(numOfClient - 1);
                    System.out.println("Client deleted");
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                input.nextLine(); // Clear the input buffer
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid input.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!valid);
    }

    public void deleteClientAccount () {
        System.out.println("--------------");
        if(Bank.accounts.isEmpty()){
            System.out.println("There is no accounts yet");
        } else {
            for (int i = 0; i < Bank.accounts.size(); i++) {
                System.out.printf("[%d]\t\tName: %-20s Account number: %-10d Status: %-10s Balance: %-10f Account type: %-25s \n",(i + 1),Bank.accounts.get(i).getClientName(),Bank.accounts.get(i).getAccountNumber(),Bank.accounts.get(i).getAccountStatus(),Bank.accounts.get(i).getBalance(),Bank.accounts.get(i).getAccountType());
            }
            boolean valid = false;
            do {
                try {
                    System.out.println("-----------");
                    System.out.println("Enter account number");
                    int numOfAccount = input.nextInt();
                    input.nextLine();
                    Bank.accounts.remove(numOfAccount - 1);
                    System.out.println("Account removed");
                    valid = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    input.nextLine(); // Clear the input buffer
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Invalid input.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (!valid);

        }
    }

    public void employeePage () {
        System.out.println("---------------");
        System.out.printf("Welcome, %s %s \n",this.getFirstName(),this.getLastName());
        while (true) {
            try {
                System.out.println("---------------");
                System.out.println("Press [1] to Display personal info :");
                System.out.println("Press [2] to edit personal info :");
                System.out.println("Press [3] to add new client :");
                System.out.println("Press [4] to create client account :");
                System.out.println("Press [5] to edit client account :");
                System.out.println("Press [6] to search for client :");
                System.out.println("Press [7] to delete client account :");
                System.out.println("Press [8] to delete client :");
                System.out.println("Press [9] to logout :");

                int choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1:
                        displayEmployeeInfo();
                        break;
                    case 2:
                        editPersonalInfo();
                        break;
                    case 3:
                        createClient();
                        break;
                    case 4:
                        createClientAccount();
                        break;
                    case 5:
                        editClientAccount();
                        break;
                    case 6:
                        searchForClient();
                        break;
                    case 7:
                        deleteClientAccount();
                        break;
                    case 8:
                        deleteClient();
                        break;
                    case 9:
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
