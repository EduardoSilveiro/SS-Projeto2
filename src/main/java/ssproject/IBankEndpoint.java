package ssproject;

/**
 * This interface is not to be instrumented. When a class is instrumented, the
 * instrumentation procedure preserves the superclasses and interfaces
 * implemented by a class.
 * <p>
 * This interface facilitates the interaction with the instrumented underlying lass,
 * allowing the cast of instrumented BankClientEndpoint class objects to this interface, and
 * avoiding the use of reflection to invoke methods in object belonging to the
 * instrumented BankClientEndpoint class.
 * <p>
 * Note that this is only necessary given that we are performing runtime
 * instrumentation, where the non-instrumented classes have already been loaded
 * by the system classloader
 */
public interface IBankEndpoint {

    default double getBalance() {
        throw new UnsupportedOperationException();
    }

    default double withdraw(double amount) {
        throw new UnsupportedOperationException();
    }

    default double deposit(double amount) {
        throw new UnsupportedOperationException();
    }

    default double transfer(int receiverId, double amount) {
        throw new UnsupportedOperationException();
    }

    default double averageBalance() {
        throw new UnsupportedOperationException();
    }

    default String getLog() {
        throw new UnsupportedOperationException();
    }

    default int getUserId() {
        throw new UnsupportedOperationException();
    }

}
