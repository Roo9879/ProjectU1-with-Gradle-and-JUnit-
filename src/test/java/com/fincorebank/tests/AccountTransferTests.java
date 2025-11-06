package com.fincorebank.tests;

import com.fincorebank.model.*;
import com.fincorebank.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

public class AccountTransferTests {
    private InMemoryDataStore dataStore;
    private ServiceHandler handler;
    private Account fromAccount;
    private Account toAccount;
    private Account sender;
    private Account receiver;

    @BeforeEach
    void setUp() {
        dataStore = new InMemoryDataStore();
        handler = new ServiceHandler(new Scanner(""), dataStore); //passing in dummy scanner for testing

        fromAccount = new Account("Alice", 1000);
        toAccount = new Account("Bob", 500);

        dataStore.addAccount(fromAccount);
        dataStore.addAccount(toAccount);

        sender = new Account("Rosie", 1000);
        receiver = new Account("Isaac", 500);

    }

    //separate class instead of adding to AccountTests as am using a TDD approach for this test
    @Test
    @DisplayName("Test successfully transferring funds between two accounts")
    void testTransferToAnotherAccount() {
        boolean result = sender.transferTo(receiver, 200);

        assertTrue(result);
        assertEquals(800, sender.getCurrentBalance());
        assertEquals(700, receiver.getCurrentBalance());
    }

    @Test
    @DisplayName("Test transfer fails with insufficient funds")
    void testTransferInsufficientFunds() {
        Account sender = new Account("Rosie", 100); //overwrite starting balance

        boolean result = sender.transferTo(receiver, 200);

        assertFalse(result);
        assertEquals(100, sender.getCurrentBalance());
        assertEquals(500, receiver.getCurrentBalance());
    }

    @Test
    @DisplayName("Test transfer fails with zero or negative amounts")
    void testTransferInvalidAmount() {
        assertFalse(sender.transferTo(receiver, -50));
        assertFalse(sender.transferTo(receiver, 0));

        assertEquals(1000, sender.getCurrentBalance());
        assertEquals(500, receiver.getCurrentBalance());
    }

    @Test
    @DisplayName("Test transfer succeeds with exact balance")
    void testTransferExactBalance() {
        sender = new Account("Rosie", 200); // overwrite starting balance
        boolean result = sender.transferTo(receiver, 200);

        assertTrue(result);
        assertEquals(0, sender.getCurrentBalance());
        assertEquals(700, receiver.getCurrentBalance());
    }

    @Test
    @DisplayName("Transfer fails when sending to same account or account not found")
    void testServiceHandlerEdgeCases() {
        assertFalse(handler.transferFunds("Alice", "Alice", 100));
        assertFalse(handler.transferFunds("Alice", "Charlie", 100));

        assertEquals(1000, fromAccount.getCurrentBalance());
        assertEquals(500, toAccount.getCurrentBalance());
    }
}
