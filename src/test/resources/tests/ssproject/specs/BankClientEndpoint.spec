package ssproject;

import ssproject.labels.Client;

public class BankClientEndpoint {

    private int userId;

    public double:<=Client(userId) getBalance();

    public double:<=Client(userId) transfer(int receiverId, double amount);

    public double:<=Client(userId) deposit(double amount);

    public double:<=Client(userId) withdraw(double amount);

    public double:>Client(userId) averageBalance();
}
