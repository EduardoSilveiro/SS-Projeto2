package ssproject;

import ssproject.labels.Client;
import ssproject.labels.Employee;

public class BankClientEndpoint {

    private int userId;

    public double:<=Client(userId) getBalance();

    public double:<=Client(userId) transfer(int:Client(receiverId) receiverId, double amount);

    public double:<=Client(userId) deposit(double amount);

    public double:<=Client(userId) withdraw(double amount);

    public double:<=Client(userId) averageBalance();

    public String:<=Client(userId) getLog();
}
