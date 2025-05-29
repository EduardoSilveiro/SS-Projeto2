package ssproject;

//import ssproject.labels.Employee;
//import ssproject.labels.Auditor;
//import ssproject.labels.Client;

public class Account {

    private final int accountId;

    private double balance;

    //private enum AccountType {"E","C","A"}

    public Account(int accountId) {
        this.accountId = accountId;
        this.balance = 0;

    }


    public double getBalance(int userId) {
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
