package com.fincorebank.tests;

import com.fincorebank.model.*;
import com.fincorebank.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTests {
    private SavingsAccount savingsAccount;

    @BeforeEach
    void setUp() {
        savingsAccount = new SavingsAccount("Tyler", 1500, 1.5);
    }

    @ParameterizedTest
    @CsvSource({
            "900, true, 600",
            "1000, true, 500",
            "1001, false, 1500"
    })
    @DisplayName("Testing withdrawals for savings accounts: amounts under 1000(limit), amount of 1000 and over limit of 1000")
    void testSavingsWithdrawal(double amount, boolean expectedResult, double expectedBalance) {
        boolean result = savingsAccount.makeWithdrawal(amount);
        assertEquals(expectedResult, result);
        assertEquals(expectedBalance, savingsAccount.getCurrentBalance());
    }

    @Test
    void testAddInterest() {
        savingsAccount.addInterest();
        assertEquals(1522.5, savingsAccount.getCurrentBalance(), 0.001);
    }

}
