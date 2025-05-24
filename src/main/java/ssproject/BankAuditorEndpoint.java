package ssproject;

public class BankAuditorEndpoint implements IBankEndpoint {

    private final IBank bank;
    private final int userId;


    public BankAuditorEndpoint(IBank bank) {
        this(bank, bank.newAccount());
    }

    public BankAuditorEndpoint(IBank bank, int userId) {
        this.bank = bank;
        this.userId = userId;
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
    public double averageBalance() {
        return bank.getAccountBalance(userId);
    }
}
