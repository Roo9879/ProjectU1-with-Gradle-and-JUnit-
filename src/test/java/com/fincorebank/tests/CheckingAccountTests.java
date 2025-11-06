package com.fincorebank.tests;

import com.fincorebank.model.*;
import com.fincorebank.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CheckingAccountTests {
    private CheckingAccount checkingAccount;

    @BeforeEach
    void setUp() {
        checkingAccount = new CheckingAccount("Carol", 300, 200);
    }

    @Test
    void testWithdrawalNegativeAmount() {
        boolean result = checkingAccount.makeWithdrawal(-200);
        assertFalse(result);
        assertEquals(300, checkingAccount.getCurrentBalance());
    }

    @Test
    void testWithdrawalExceedingOverdraftAmount() {
        boolean result = checkingAccount.makeWithdrawal(600);
        assertFalse(result);
        assertEquals(300, checkingAccount.getCurrentBalance());
    }

    @Test
    void testWithdrawalAtOverdraftAmount() {
        boolean result = checkingAccount.makeWithdrawal(500);
        assertTrue(result);
        assertEquals(-200, checkingAccount.getCurrentBalance());
    }

    @Test
    void testWithdrawalWithinBalancePlusOverdraftAmount() {
        boolean result = checkingAccount.makeWithdrawal(400);
        assertTrue(result);
        assertEquals(-100, checkingAccount.getCurrentBalance());
    }

}
