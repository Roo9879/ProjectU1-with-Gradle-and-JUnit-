package com.fincorebank.tests;

import com.fincorebank.model.*;
import com.fincorebank.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTests {
    private SavingsAccount savingsAccount;

    @BeforeEach
    void setUp() {
        savingsAccount = new SavingsAccount("Tyler", 1500, 1.5);
    }

    @Test
    void testAddInterest() {
        savingsAccount.addInterest();
        assertEquals(1522.5, savingsAccount.getCurrentBalance(), 0.001);
    }

    @Test
    void testWithdrawalUnder1000() {
        boolean result = savingsAccount.makeWithdrawal(900);
        assertTrue(result);
        assertEquals(600, savingsAccount.getCurrentBalance());
    }

    @Test
    void testMaxWithdrawalOf1000() {
        boolean result = savingsAccount.makeWithdrawal(1000);
        assertTrue(result);
        assertEquals(500, savingsAccount.getCurrentBalance());
    }

    @Test
    void testOverMaxWithdrawal() {
        boolean result = savingsAccount.makeWithdrawal(1001);
        assertFalse(result);
        assertEquals(1500, savingsAccount.getCurrentBalance());
    }

}
