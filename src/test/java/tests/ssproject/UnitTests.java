package tests.ssproject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import snitch.instrumentor.envs2.ProgramInstrumentationEnvironment;
import snitch.instrumentor.rewriters.klass.ClassRewriter;
import snitch.monitor.classloaders.SnitchClassLoader;
import snitch.specifications.ClassSpecification;
import snitch.specifications.ProgramSpecification;
import snitch.specifications.parser.AbstractSpecificationParser;
import snitch.specifications.parser.internals.BaseListener;
import ssproject.*;

import java.io.File;
import java.io.FileInputStream;


public class UnitTests {

    private static final SnitchClassLoader loader;

    static {
        loader = SnitchClassLoader.getMutableInstance();
    }


    private static ClassSpecification parseSpecificationFile(File specFile) {
        try {
            final var in = new FileInputStream(specFile);

            final var parser = AbstractSpecificationParser.instance(in, new BaseListener(ClassLoader.getSystemClassLoader()));

            return parser.parseClassDeclaration();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static File loadResource(String name) {
        return new File(UnitTests.class.getResource(name).getFile());
    }

    private static ProgramSpecification parseSpecificationFiles() {
        final var spec = new ProgramSpecification();

        spec.addClassSpecification(parseSpecificationFile(loadResource("specs/Account.spec")));
        spec.addClassSpecification(parseSpecificationFile(loadResource("specs/Bank.spec")));
        spec.addClassSpecification(parseSpecificationFile(loadResource("specs/Log.spec")));
        spec.addClassSpecification(parseSpecificationFile(loadResource("specs/BankClientEndpoint.spec")));
        spec.addClassSpecification(parseSpecificationFile(loadResource("specs/BankEmployeeEndpoint.spec")));
        spec.addClassSpecification(parseSpecificationFile(loadResource("specs/BankAuditorEndpoint.spec")));

        return spec;
    }


    private static void instrumentAndLoad(ClassRewriter rewriter, Class<?> klass) {
        loader.addNewClass(klass.getCanonicalName(), rewriter.instrument(klass).getBytecode());
    }


    private static IBank newBank() {
        try {
            final var bankInstrumentedClass = loader.loadClass(Bank.class.getCanonicalName());

            final var constructor = bankInstrumentedClass.getConstructor();

            return (IBank) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static IBankEndpoint newBankEndpoint(Class<? extends IBankEndpoint> endpointType, IBank bank) {
        try {
            final var endpointInstrumentedClass = loader.loadClass(endpointType.getCanonicalName());

            final var constructor = endpointInstrumentedClass.getConstructor(IBank.class);

            return (IBankEndpoint) constructor.newInstance(bank);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static IBankEndpoint newBankEndpoint(Class<? extends IBankEndpoint> endpointType, IBank bank, int userId) {
        try {
            final var endpointInstrumentedClass = loader.loadClass(endpointType.getCanonicalName());

            final var constructor = endpointInstrumentedClass.getConstructor(IBank.class, int.class);

            return (IBankEndpoint) constructor.newInstance(bank, userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public static void prepare() {
        final var specification = parseSpecificationFiles();

        final var rewriter = new ClassRewriter(new ProgramInstrumentationEnvironment(specification));

        instrumentAndLoad(rewriter, Account.class);
        instrumentAndLoad(rewriter, Bank.class);
        instrumentAndLoad(rewriter, Log.class);
        instrumentAndLoad(rewriter, BankClientEndpoint.class);
        instrumentAndLoad(rewriter, BankEmployeeEndpoint.class);
        instrumentAndLoad(rewriter, BankAuditorEndpoint.class);
    }


    @Test
    public void testGetBalanceOK() {
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint endpoint = newBankEndpoint(BankClientEndpoint.class, bank, id0);

        endpoint.getBalance();
    }


    @Test
    public void testGetBalanceNotOK() {
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint endpoint = newBankEndpoint(BankClientEndpoint.class, bank, id1);

        endpoint.getBalance();
    }

    @Test
    public void testCheck() {
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();


        final IBankEndpoint endpoint0 = newBankEndpoint(BankClientEndpoint.class, bank, id0);

        final IBankEndpoint endpoint1 = newBankEndpoint(BankClientEndpoint.class, bank, id1);

        endpoint0.getBalance();

        endpoint1.getBalance();
    }

    @Test
    public void testCheck2() {
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        bank.newAccount();
        final IBankEndpoint endpoint0 = newBankEndpoint(BankClientEndpoint.class, bank, id0);

        final IBankEndpoint endpoint1 = newBankEndpoint(BankClientEndpoint.class, bank, id1);
        endpoint0.deposit(100.0);
        endpoint1.deposit(100.0);
        endpoint0.transfer(id0, 100.0);
        endpoint1.transfer(id1, 100.0);

    }

}
