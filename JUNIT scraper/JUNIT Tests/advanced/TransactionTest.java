package design;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import design.Transaction.TransactionType;

public class TransactionTest {

    private static final Date TODAY = Calendar.getInstance().getTime();
    
    @Test
    public void testConstructors() {
        Account account1 = new CurrentAccount("0001", "Bob");
        Account account2 = new CurrentAccount("0002", "Fred");

        Transaction transaction = new Deposit(account1, TODAY, new BigDecimal("20.50"));
        assertEquals(account1, transaction.getTargetAccount());
        assertEquals(TODAY, transaction.getDate());
        assertEquals(new BigDecimal("20.50"), transaction.getAmount());
        assertEquals(TransactionType.DEPOSIT, transaction.getType());

        transaction = new Withdrawal(account1, TODAY, new BigDecimal("20.50"));
        assertEquals(account1, transaction.getTargetAccount());
        assertEquals(TODAY, transaction.getDate());
        assertEquals(new BigDecimal("20.50"), transaction.getAmount());
        assertEquals(TransactionType.WITHDRAWAL, transaction.getType());

        transaction = new InterestPayment(account1, TODAY, new BigDecimal("20.50"));
        assertEquals(account1, transaction.getTargetAccount());
        assertEquals(TODAY, transaction.getDate());
        assertEquals(new BigDecimal("20.50"), transaction.getAmount());
        assertEquals(TransactionType.INTEREST_PAYMENT, transaction.getType());

        transaction = new Transfer(account1, account2, TODAY, new BigDecimal("20.50"));
        assertEquals(account1, ((Transfer) transaction).getSourceAccount());
        assertEquals(account2, transaction.getTargetAccount());
        assertEquals(TODAY, transaction.getDate());
        assertEquals(new BigDecimal("20.50"), transaction.getAmount());
        assertEquals(TransactionType.TRANSFER, transaction.getType());
    }

}
