package com.fincorebank.tests;

import com.fincorebank.model.*;
import com.fincorebank.service.*;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataStoreTests {
    private InMemoryDataStore dataStore;
    private Account account;

    @BeforeEach
    void setUp() {
        dataStore = new InMemoryDataStore();
        account = new Account("Vinnie", 500);
    }

    @Test
    void testAddAndFindAccountByNumber() {
        dataStore.addAccount(account);
        Account found = dataStore.findAccountByNumber(account.getAccountNumber());
        assertNotNull(found);
        assertEquals("Vinnie", found.getAccountHolderName());
    }

    @Test
    void testAddAndFindAccountByName() {
        dataStore.addAccount(account);
        Account found = dataStore.findAccountByName(account.getAccountHolderName());
        assertNotNull(found);
        assertEquals("Vinnie", found.getAccountHolderName());
    }

    @Test
    void testFailingFindAccountByName() {
        dataStore.addAccount(account);
        Account found = dataStore.findAccountByName("alice");
        assertNull(found);
    }

    @Test
    void testAccountDeletion() {
        dataStore.addAccount(account);
        dataStore.deleteAccount(account.getAccountNumber());
        Account notFound = dataStore.findAccountByNumber(account.getAccountNumber());
        assertNull(notFound);
    }

    @Test
    void testUpdateAccount() {
        dataStore.addAccount(account);
        account.setAccountHolderName("Alice");
        account.makeDeposit(200);
        dataStore.updateAccount(account);

        Account updated = dataStore.findAccountByNumber(account.getAccountNumber());
        assertEquals("Alice", updated.getAccountHolderName());
        assertEquals(700, updated.getCurrentBalance());
    }

    @Test
    void testAddTransaction() {
        dataStore.addAccount(account);
        Transaction deposit = new Transaction(account.getAccountNumber(), 200, "Deposit");
        dataStore.addTransaction(account.getAccountNumber(), deposit);

        List<Transaction> transactions = dataStore.getTransactionsForAccount(account.getAccountNumber());
        assertEquals(1, transactions.size());
        assertEquals(200, transactions.get(0).getAmount());
        assertEquals("Deposit", transactions.get(0).getType());
    }

    @Test
    void testTransactionSorting() {
        dataStore.addAccount(account);
        dataStore.addTransaction(account.getAccountNumber(), new Transaction(account.getAccountNumber(), 300, "Deposit"));
        dataStore.addTransaction(account.getAccountNumber(), new Transaction(account.getAccountNumber(), 100, "Withdrawal"));
        dataStore.addTransaction(account.getAccountNumber(), new Transaction(account.getAccountNumber(), 200, "Deposit"));

        List<Transaction> sortedByAmount = dataStore.getSortedTransactions(account.getAccountNumber(), Comparator.comparing(Transaction::getAmount));
        assertEquals(100, sortedByAmount.get(0).getAmount());
        assertEquals(200, sortedByAmount.get(1).getAmount());
        assertEquals(300, sortedByAmount.get(2).getAmount());
    }

}
