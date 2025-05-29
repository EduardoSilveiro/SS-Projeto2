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
        throw new UnsupportedOperationException("Auditors can´t access this method");
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public double averageBalance() {
        return bank.getAverageBalance();
    }

    public void getUserType() {
        System.out.println("User type: Auditor");
    }
}
