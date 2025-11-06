package com.fincorebank.tests;

import com.fincorebank.model.*;
import com.fincorebank.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AccountTests {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("Vinnie", 500);
    }

    @ParameterizedTest
    @CsvSource({
            "-200, false, 500",
            "0, false, 500",
            "200, true, 700"
    })
    @DisplayName("Tests making a deposit with a negative amount, amount of zero, and positive amount")
    void testMakeDeposit(double amount, boolean expectedResult, double expectedBalance) {
        boolean result = account.makeDeposit(amount);
        assertEquals(expectedResult, result);
        assertEquals(expectedBalance, account.getCurrentBalance());
    }

    @ParameterizedTest
    @CsvSource({
            "200, true, 300",
            "-200, false, 500",
            "0, false, 500",
            "600, false, 500"
    })
    @DisplayName("Tests making a withdrawal with a positive and negative amount, an amount of zero and an amount too high")
    void testMakeWithdrawal(double amount, boolean expectedResult, double expectedBalance) {
        boolean result = account.makeWithdrawal(amount);
        assertEquals(expectedResult, result);
        assertEquals(expectedBalance, account.getCurrentBalance());
    }

}
