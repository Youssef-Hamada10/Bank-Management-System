package package1.CreditCard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CreditCard {

    // object from Scanner
    Scanner input = new Scanner(System.in);

    // attribute
//    private final double limit;
//    private double remainingLimit ;
    private final int accountNumber;
    private String status;
    private int loyaltyPoints = 0;
    private String date;
    private String expDate;

    // constructor
    public CreditCard(int accountNumber) {
        this.accountNumber = accountNumber;
        this.status = "disabled";
//        limit = 20000;
//        remainingLimit = limit;
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        this.date = now.format(formatter);
//        this.expDate = now.plusYears(4).format(formatter);
    }

    // getters and setters

    public int getAccountNumber() {
        return accountNumber;
    }

//    public double getRemainingLimit() {
//        return remainingLimit;
//    }
//
//    public void setRemainingLimit(double remainingLimit) {
//        this.remainingLimit = remainingLimit;
//    }
//
//    public double getLimit() {
//        return limit;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
}
