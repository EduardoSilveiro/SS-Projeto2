package ssproject;

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
        return getAccount(accountId).getBalance();
    }

    @Override
    public double withdraw(int accountId, double amount) {
        final var account = getAccount(accountId);
        final var accountBalance = account.getBalance();

        if (accountBalance < amount) {
            Log.getInstance().logFailedWithdrawal(accountId, amount, Double.toString(accountBalance));
        } else {
            Log.getInstance().logSuccessfulWithdrawal(accountId, amount);
            account.withdraw(amount);
        }

        return account.getBalance();
    }

    @Override
    public double deposit(int accountId, double amount) {
        final var account = getAccount(accountId);

        Log.getInstance().logDeposit(accountId, account.getBalance());

        account.deposit(amount);

        return account.getBalance();
    }


    @Override
    public double transfer(int senderId, int receiverId, double amount) {
        final var sender = getAccount(senderId);
        final var senderBalance = sender.getBalance();
        final var receiver = getAccount(receiverId);

        if (senderBalance < amount) {
            Log.getInstance().logFailedTransaction(senderId, receiverId, amount, Double.toString(senderBalance));
        } else {
            Log.getInstance().logSuccessfulTransaction(senderId, receiverId, amount);
            sender.withdraw(amount);
            double validatedAmount = newAmountForReceiver(amount);;
            deposit(receiverId, validatedAmount);
        }

        return sender.getBalance();
    }

    private double newAmountForReceiver(double amount) {
        return amount;
    }


    @Override
    public double getAverageBalance() {
        var sum = 0.0;

        for (var account : accounts)
            sum += account.getBalance();

        return sum / accounts.size();
    }

    @Override
    public String getLog() {
        return Log.getInstance().getLog();
    }

}
