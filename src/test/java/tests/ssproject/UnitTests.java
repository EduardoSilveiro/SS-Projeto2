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


    //Deve funcionar, porque mudamos o id de 0 para userId
    @Test
    public void testGetBalanceOK() {
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint endpoint = newBankEndpoint(BankClientEndpoint.class, bank, id0);

        endpoint.getBalance();
    }

    //Deve funcionar, porque mudamos o id de 0 para userId
    @Test
    public void testGetBalanceNotOK() {
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint endpoint = newBankEndpoint(BankClientEndpoint.class, bank, id1);

        endpoint.getBalance();
    }

    //Deve funcionar porque os employees podem ver o balance das contas
    @Test
    public void testEmployeesBankAccountAcess(){
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint clientEndpoint = newBankEndpoint(BankClientEndpoint.class, bank, id0);

        final IBankEndpoint employeeEndpoint = newBankEndpoint(BankEmployeeEndpoint.class, bank, id1);

        employeeEndpoint.averageBalance();

        employeeEndpoint.getBalance();
    }
    //Information Leak!
    // Cliente  não pode ver o averageBalance é suposto dar
    @Test
    public void test_Client_Can_Not_Acess_Average_Balance(){
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint clientEndpoint = newBankEndpoint(BankClientEndpoint.class, bank, id0);

        final IBankEndpoint employeeEndpoint = newBankEndpoint(BankEmployeeEndpoint.class, bank, id1);

        clientEndpoint.averageBalance();

    }


    //Employee pode ver o averageBalance
    @Test
    public void test_Employee_Can_Acess_Average_Balance(){
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint employeeEndpoint1 = newBankEndpoint(BankEmployeeEndpoint.class, bank, id0);

        final IBankEndpoint employeeEndpoint2 = newBankEndpoint(BankEmployeeEndpoint.class, bank, id1);

        employeeEndpoint1.averageBalance();
        employeeEndpoint2.averageBalance();
    }

    //Auditor pode ver o averageBalance
    @Test
    public void testAuditorAverage(){
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint auditorEndpoint = newBankEndpoint(BankAuditorEndpoint.class, bank, id0);
        final IBankEndpoint clientEndpoint = newBankEndpoint(BankClientEndpoint.class, bank, id1);

        clientEndpoint.deposit(100.0);
        clientEndpoint.getBalance();
        auditorEndpoint.averageBalance();
    }



    //Este teste deve funcionar, pois o employee pode ver o logs
    @Test
    public void testLogsMustNotIncludeAccInfo(){
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint employeeEndpoint = newBankEndpoint(BankEmployeeEndpoint.class, bank, id1);

        System.out.println(employeeEndpoint.getLog());

    }

    //O employee pode ver o logs
    @Test
    public void testLogsEmployee(){
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint employeeEndpoint = newBankEndpoint(BankEmployeeEndpoint.class, bank, id1);

        employeeEndpoint.getLog();

    }
    //Information Leak!
    //O cliente não pode ver o logs
    @Test
    public void testLogsClientCanNotAcess(){
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint clientEndpoint = newBankEndpoint(BankClientEndpoint.class, bank, id0);

        clientEndpoint.getLog();
    }






    /**
     * Para ultimo
     */
    @Test
    public void testClientTransfer() {
        final IBank bank = newBank();

        final var id0 = bank.newAccount();
        final var id1 = bank.newAccount();

        final IBankEndpoint clientEndpoint0 = newBankEndpoint(BankClientEndpoint.class, bank, id0);
        final IBankEndpoint clientEndpoint1 = newBankEndpoint(BankClientEndpoint.class, bank, id1);

        clientEndpoint0.deposit(100.0);

        clientEndpoint0.transfer(clientEndpoint1.getUserId(), 5.0);

    }

}
