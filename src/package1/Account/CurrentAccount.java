package package1.Account;

public class CurrentAccount extends Account{
    // attributes
    private final double minimumBalance;
    private final double fees;

    // constructor
    public CurrentAccount(String clientName, double balance) {
        super(clientName, balance, "current account");
        minimumBalance = 3000;
        fees = 500;
    }
    public CurrentAccount(){
        super();
        minimumBalance = 3000;
        fees = 500;
    }

    // getters and setters
    public double getMinimumBalance() {
        return minimumBalance;
    }

    public double getFees() {
        return fees;
    }

}
