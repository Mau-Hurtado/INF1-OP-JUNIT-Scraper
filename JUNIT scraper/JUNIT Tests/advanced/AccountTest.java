package design;

import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class AccountTest {

    private static final Date TEST_DATE;
    static {
       Calendar calendar = Calendar.getInstance();
       calendar.set(2016, 0, 16);
       TEST_DATE = calendar.getTime();
    }
    
    @Test
    public void testCreateAccounts() {
        Account account = new CurrentAccount("0001", "Bob");
        assertEquals("0001", account.getAccountNumber());
        assertEquals("Bob", account.getAccountHolder());
        assertEquals(ZERO, account.getAccountBalance());
        
        account = new SavingsAccount("0001", "Bob");
        assertEquals("0001", account.getAccountNumber());
        assertEquals("Bob", account.getAccountHolder());
        assertEquals(ZERO, account.getAccountBalance());
        
        JointAccount jointAccount = new JointCurrentAccount("0001", "Bob", "Fred");
        assertEquals("0001", jointAccount.getAccountNumber());
        assertEquals("Bob", jointAccount.getAccountHolder());
        assertEquals("Fred", jointAccount.getSecondAccountHolder());
        assertEquals(ZERO, jointAccount.getAccountBalance());
        
        jointAccount = new JointSavingsAccount("0001", "Bob", "Fred");
        assertEquals("0001", jointAccount.getAccountNumber());
        assertEquals("Bob", jointAccount.getAccountHolder());
        assertEquals("Fred", jointAccount.getSecondAccountHolder());
        assertEquals(ZERO, jointAccount.getAccountBalance());
    }

    @Test
    public void testAddTransaction_unknownAccount() {
        account1 = new CurrentAccount("0001", "Bob");
        account2 = new CurrentAccount("0002", "Fred");
        try {
            Transaction transaction = new Deposit(account1, TEST_DATE, new BigDecimal("20.50"));
            account2.addTransaction(transaction);
            fail("Transaction does not match account - should have thrown RuntimeException");
        }
        catch (RuntimeException ex) {
            // Expected exception
        }
    }

    @Test
    public void testAddTransaction_invalidAccountType() {
        Account account = new SavingsAccount("0001", "Bob");
        try {
            Transaction transaction = new Deposit(account, TEST_DATE, new BigDecimal("20.50"));
            account.addTransaction(transaction);
            fail("Transaction type not valid for account - should have thrown RuntimeException");
        }
        catch (RuntimeException ex) {
            // Expected exception
        }
        
        try {
            Transaction transaction = new Withdrawal(account, TEST_DATE, new BigDecimal("20.50"));
            account.addTransaction(transaction);
            fail("Transaction type not valid for account - should have thrown RuntimeException");
        }
        catch (RuntimeException ex) {
            // Expected exception
        }

        account = new CurrentAccount("0001", "Bob");
        try {
            Transaction transaction = new InterestPayment(account, TEST_DATE, new BigDecimal("20.50"));
            account.addTransaction(transaction);
            fail("Transaction type not valid for account - should have thrown RuntimeException");
        }
        catch (RuntimeException ex) {
            // Expected exception
        }
        
        account = new JointSavingsAccount("0001", "Bob", "Fred");
        try {
            Transaction transaction = new Deposit(account, TEST_DATE, new BigDecimal("20.50"));
            account.addTransaction(transaction);
            fail("Transaction type not valid for account - should have thrown RuntimeException");
        }
        catch (RuntimeException ex) {
            // Expected exception
        }
        
        try {
            Transaction transaction = new Withdrawal(account, TEST_DATE, new BigDecimal("20.50"));
            account.addTransaction(transaction);
            fail("Transaction type not valid for account - should have thrown RuntimeException");
        }
        catch (RuntimeException ex) {
            // Expected exception
        }

        account = new JointCurrentAccount("0001", "Bob", "Fred");
        try {
            Transaction transaction = new InterestPayment(account, TEST_DATE, new BigDecimal("20.50"));
            account.addTransaction(transaction);
            fail("Transaction type not valid for account - should have thrown RuntimeException");
        }
        catch (RuntimeException ex) {
            // Expected exception
        }
    }

    private Account account1;
    private Account account2;
    private Account account3;
    private Account account4;

    
    private void setupAccounts() {
        account1 = new CurrentAccount("0001", "Bob");
        account2 = new SavingsAccount("0002", "Fred");
        account3 = new JointCurrentAccount("0003", "Bob", "Fred");
        account4 = new JointSavingsAccount("0004", "Fred", "Alice");
        
        assertEquals("", account1.printTransactions());
        assertEquals(ZERO, account1.getAccountBalance());
        
        account1.addTransaction(new Deposit(account1, TEST_DATE, new BigDecimal("20.50")));
        account1.addTransaction(new Withdrawal(account1, TEST_DATE, new BigDecimal("10.50")));
        
        Transaction transfer = new Transfer(account1, account3, TEST_DATE, new BigDecimal("5.00"));
        account1.addTransaction(transfer);
        account3.addTransaction(transfer);
        
        account2.addTransaction(new InterestPayment(account2, TEST_DATE, new BigDecimal("3.14")));
        
        account3.addTransaction(new Deposit(account3, TEST_DATE, new BigDecimal("1000.00")));
        transfer = new Transfer(account3, account4, TEST_DATE, new BigDecimal("500.00"));
        account3.addTransaction(transfer);
        account4.addTransaction(transfer);
    }

    /**
     * Convert Windows format linefeeds to Unix/Linux/OSX format for string comparison
     * @param input input string containing newlines in '\n', '\r', '\r\n' format
     * @return string containing only '\n' newlines
     */
    private String convertToUnix(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("\r\n", "\n").replaceAll("\r", "\n");
    }

    @Test
    public void testPrintTransactions() {
        setupAccounts();

        assertEquals("16/01/2016 : DEPOSIT : £20.50\n" + 
                "16/01/2016 : WITHDRAWAL : £10.50\n" + 
                "16/01/2016 : TRANSFER (from account 0001 to account 0003) : £5.00", 
                convertToUnix(account1.printTransactions().trim()));
        
        assertEquals("16/01/2016 : INTEREST_PAYMENT : £3.14", 
                account2.printTransactions().trim());
        
        assertEquals("16/01/2016 : TRANSFER (from account 0001 to account 0003) : £5.00\n" + 
                "16/01/2016 : DEPOSIT : £1000.00\n" + 
                "16/01/2016 : TRANSFER (from account 0003 to account 0004) : £500.00", 
                convertToUnix(account3.printTransactions().trim()));
        
        assertEquals("16/01/2016 : TRANSFER (from account 0003 to account 0004) : £500.00", 
                account4.printTransactions().trim());
    }

    @Test
    public void testGetBalance() {
        setupAccounts();

        assertEquals(new BigDecimal("5.00"), account1.getAccountBalance());
        assertEquals(new BigDecimal("3.14"), account2.getAccountBalance());
        assertEquals(new BigDecimal("505.00"), account3.getAccountBalance());
        assertEquals(new BigDecimal("500.00"), account4.getAccountBalance());
    }

    @Test
    public void testToString() {
        setupAccounts();

        assertEquals("Current account 0001 : Bob : £5.00", account1.toString());
        assertEquals("Savings account 0002 : Fred : £3.14", account2.toString());
        assertEquals("Joint current account 0003 : Bob, Fred : £505.00", account3.toString());
        assertEquals("Joint savings account 0004 : Fred, Alice : £500.00", account4.toString());
    }

}
