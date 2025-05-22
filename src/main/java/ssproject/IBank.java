package ssproject;

/**
 * This interface is not to be instrumented. Allows the use of the
 * NON-INSTRUMENTED and the INSTRUMENTED Bank class interchangeably.
 *
 * It is required to pass an instrumented Bank to the constructor of
 * the BankClientEndpoint class.
 *
 * Note that this is only necessary given that we are performing runtime
 * instrumentation, where the non-instrumented classes have already been loaded
 * by the system classloader
 */
public interface IBank {

    int newAccount();

    double getAccountBalance(int accountId);

    double withdraw(int accountId, double amount);

    double deposit(int accountId, double amount);

    double transfer(int senderId, int receiverId, double amount);

    double getAverageBalance();

    String getLog();

}
