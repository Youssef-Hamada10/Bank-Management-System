package package1.Account;

public class SavingAccount extends Account{
    // attributes
    private static final double interestRate = 0.75;

    // getters and setters
    public double getInterestRate() {
        return interestRate;
    }

    // constructor
    public SavingAccount(String clientName, double balance) {
        super(clientName, balance, "saving account");
    }

    public SavingAccount(){
        super();
    }

    // implement method
    public void applyInterest() {
        super.setBalance(super.getBalance() + (super.getBalance() * SavingAccount.interestRate));
        System.out.println("Interest applied. Your current Balance is : " + super.getBalance());
    }

}
