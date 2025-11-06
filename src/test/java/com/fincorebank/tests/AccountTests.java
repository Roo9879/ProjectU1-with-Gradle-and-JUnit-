package com.fincorebank.tests;

import com.fincorebank.model.*;
import com.fincorebank.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTests {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("Vinnie", 500);
    }

    @Test
    void testDepositNegativeAmount() {
        boolean result = account.makeDeposit(-200);
        assertFalse(result); //checking if makeDeposit failed like it should
        assertEquals(500, account.getCurrentBalance()); //checking that balance has stayed the same
    }

    @Test
    void testDepositZeroAmount() {
        boolean result = account.makeDeposit(0);
        assertFalse(result);
        assertEquals(500, account.getCurrentBalance());
    }

    @Test
    void testDepositPositiveAmount() {
        boolean result = account.makeDeposit(200);
        assertTrue(result);
        assertEquals(700, account.getCurrentBalance());
    }

    @Test
    void testWithdrawalPositiveAmount() {
        boolean result = account.makeWithdrawal(200);
        assertTrue(result);
        assertEquals(300, account.getCurrentBalance());
    }

    @Test
    void testWithdrawalNegativeAmount() {
        boolean result = account.makeWithdrawal(-200);
        assertFalse(result);
        assertEquals(500, account.getCurrentBalance());
    }

    @Test
    void testWithdrawalZeroAmount() {
        boolean result = account.makeWithdrawal(0);
        assertFalse(result);
        assertEquals(500, account.getCurrentBalance());
    }

    @Test
    void testWithdrawalAmountTooHigh() {
        boolean result = account.makeWithdrawal(600);
        assertFalse(result);
        assertEquals(500, account.getCurrentBalance());
    }


}
