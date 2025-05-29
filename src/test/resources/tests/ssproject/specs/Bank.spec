package ssproject;

import ssproject.labels.Client;


class Bank {

    public Account:<=Client(accountId) getAccount(int accountId);

    public double:<=Client(accountId) getAccountBalance(int:Client(accountId) accountId);

    public double:<=Client(accountId) withdraw(int:Client(accountId) accountId, double amount);

    public double:<=Client(accountId) deposit(int:Client(accountId) accountId, double:Client(_bot_) amount);

    public double:<=Client(senderId) transfer(int senderId, int receiverId, double:Client(_bot_) amount);

}
