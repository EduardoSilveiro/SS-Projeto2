package ssproject;

public class BankEmployeeEndpoint implements IBankEndpoint {

    private final IBank bank;
    private final int userId;


    public BankEmployeeEndpoint(IBank bank) {
        this(bank, bank.newAccount());
    }

    public BankEmployeeEndpoint(IBank bank, int userId) {
        this.bank = bank;
        this.userId = userId;
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

    @Override
    public double getBalance() {
        return bank.getAccountBalance(this.userId);
    }
}
