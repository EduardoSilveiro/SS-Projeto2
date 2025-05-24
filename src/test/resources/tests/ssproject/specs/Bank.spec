package ssproject;

import ssproject.labels.Client;


class Bank {


    public Account:<=Client(accountId) getAccount(int accountId);

    public double:<=Client(accountId) getAccountBalance(int:Client(accountId) accountId);

    public double:<=Client(accountId) withdraw(int:Client(accountId) accountId, double amount);

    public double:<=Client(accountId) deposit(int:Client(accountId) accountId, double amount);

    public double:<=Client(accountId) transfer(int accountId, int receiverId, double amount);

}
