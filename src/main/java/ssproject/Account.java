package ssproject;

public class Account {

    private final int accountId;

    private double balance;


    public Account(int accountId) {
        this.accountId = accountId;
    }


    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void deposit(double amount) {
        balance += amount;
    }

}
