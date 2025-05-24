package ssproject;

public class Log {

    private static final Log instance;

    static {
        instance = new Log();
    }

    public static Log getInstance() {
        return instance;
    }


    private final StringBuilder log;


    private Log() {
        log = new StringBuilder();
    }


    public void logAccountCreation(int accountId) {
        //log.append("Account ").append(accountId).append(" was successfully created.\n");
        log.append("Account ").append(" was successfully created.\n");    }

    public void logSuccessfulWithdrawal(int accountId, double amount) {
        //log.append("Account ").append(accountId).append(" successfully withdraw '").append(amount).append("\n");
        log.append("Account ").append(" successfully withdraw '").append(amount).append("\n");
    }

    public void logFailedWithdrawal(int accountId, double amount, String extraInfo) {
        //log.append("Account ").append(accountId).append(" failed to withdraw '").append(amount)
        //        .append(" due to insufficient balance (").append(extraInfo).append(")\n");
        log.append("Account ").append(" failed to withdraw '").append(amount).append(" due to insufficient balance (").append(")\n");
    }

    public void logDeposit(int accountId, double amount) {
        //log.append("Account ").append(accountId).append(" deposited '").append(amount).append("\n");
        log.append("Account ").append(" deposited '").append(amount).append("\n");
    }

    public void logSuccessfulTransaction(int sender, int receiver, double amount) {
        //log.append("Client ").append(sender).append(" successfully sent ").append(amount).append(" to ").append(receiver).append("\n");
        log.append("Client ").append(" successfully sent ").append(amount).append(" to ").append("\n");;
    }

    public void logFailedTransaction(int sender, int receiver, double amount, String extraInfo) {
        log
                .append("Client ").append(" failed to send ")
                .append(amount).append(" to ").append(" due to insufficient balance (")
                .append(")\n");
    }


    public String getLog() {
        return log.toString();
    }
}
