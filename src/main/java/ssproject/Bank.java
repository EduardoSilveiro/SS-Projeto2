package ssproject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Bank implements IBank {

    private final List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }


    @Override
    public int newAccount() {
        final var accountId = accounts.size();

        accounts.add(new Account(accountId));
        Log.getInstance().logAccountCreation(accountId);

        return accountId;
    }


    private Account getAccount(int accountId) {
        return accounts.get(accountId);
    }


    @Override
    public double getAccountBalance(int accountId) {
        return getAccount(accountId).getBalance(accountId);
    }

    @Override
    public double withdraw(int accountId, double amount) {
        final var account = getAccount(accountId);
        final var accountBalance = account.getBalance(accountId);

        if (accountBalance < amount) {
            Log.getInstance().logFailedWithdrawal(accountId, amount, Double.toString(accountBalance));
        } else {
            Log.getInstance().logSuccessfulWithdrawal(accountId, amount);
            account.withdraw(accountId, amount);
        }

        return account.getBalance(accountId);
    }

    @Override
    public double deposit(int accountId, double amount) {
        System.out.println("Entered deposit of bank");
        final var account = getAccount(accountId);
        System.out.println("Passed the getAccount");

        Log.getInstance().logDeposit(accountId, account.getBalance(accountId));

        System.out.println("Before deposit");
        account.deposit(accountId, amount);
        System.out.println("Going to print balance");
        return account.getBalance(accountId);
    }


    @Override
    public double transfer(int senderId, int receiverId, double amount) {
        final var sender = getAccount(senderId);
        final var senderBalance = sender.getBalance(senderId);
        final var receiver = getAccount(receiverId);

        if (senderBalance < amount) {
            Log.getInstance().logFailedTransaction(senderId, receiverId, amount, Double.toString(senderBalance));
        } else {
            Log.getInstance().logSuccessfulTransaction(senderId, receiverId, amount);
            sender.withdraw(senderId, amount);

            System.out.println("ENTERING DEPOSIT NOW");
            receiver.deposit(receiverId, amount);
            //deposit(receiverId, amount);
        }

        return sender.getBalance(senderId);
    }




    @Override
    public double getAverageBalance() {
        var sum = 0.0;

        for (var account : accounts)

            sum += account.getBalance(account.getAccountId());

        return sum / accounts.size();
    }

    @Override
    public String getLog() {
        return Log.getInstance().getLog();
    }

}
