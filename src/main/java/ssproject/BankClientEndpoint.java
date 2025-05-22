package ssproject;

public class BankClientEndpoint implements IBankEndpoint {

    private IBank bank;
    private int userId;


    public BankClientEndpoint(IBank bank) {
        this(bank, bank.newAccount());
    }

    public BankClientEndpoint(IBank bank, int userId) {
        this.bank = bank;
        this.userId = userId;
    }


    @Override
    public double getBalance() {
        return bank.getAccountBalance(this.userId);
    }

    @Override
    public double withdraw(double amount) {
        return bank.withdraw(userId, amount);
    }

    @Override
    public double deposit(double amount) {
        return bank.deposit(userId, amount);
    }

    @Override
    public double transfer(int receiverId, double amount) {
        return bank.transfer(receiverId, userId, amount);
    }

    @Override
    public double averageBalance() {
        return bank.getAccountBalance(userId);
    }

    @Override
    public String getLog() {
        return bank.getLog();
    }

    @Override
    public int getUserId() {
        return userId;
    }
}
