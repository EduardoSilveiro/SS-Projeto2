package ssproject;

public class Account {

    private final int accountId;

    private double balance;


    public Account(int accountId) {
        this.accountId = accountId;
        this.balance = 0;
    }


    public double getBalance(int userId) {
        System.out.println("A entrar no deposit do Account");
        if(this.accountId == userId) {
            return balance;
        }
        else {
            throw new IllegalArgumentException("Account does not belong to the user");
        }
    }

    public void withdraw(int userId, double amount) {
        System.out.println("A entrar no deposit do Account");
        if(this.accountId == userId) {
            balance -= amount;
        }
        else {
            throw new IllegalArgumentException("Account does not belong to the user");
        }
    }

    public void deposit(int userId, double amount) {
        System.out.println("A entrar no deposit do Account");
        if(this.accountId == userId) {
            balance += amount;
        }
        else {
            throw new IllegalArgumentException("Account does not belong to the user");
        }
    }

    public int getAccountId() {
        return accountId;
    }

}
