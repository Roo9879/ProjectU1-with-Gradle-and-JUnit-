package com.fincorebank.tests;

import com.fincorebank.model.*;
import com.fincorebank.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CheckingAccountTests {
    private CheckingAccount checkingAccount;

    @BeforeEach
    void setUp() {
        checkingAccount = new CheckingAccount("Carol", 300, 200);
    }

    @ParameterizedTest
    @CsvSource({
            "-200, false, 300",
            "600, false, 300",
            "500, true, -200",
            "400, true, -100"
    })
    @DisplayName("Testing making a withdrawal with a positive and negative amount, an amount exceeding the overdraft limit and meeting the limit")
    void testMakeWithdrawal(double amount, boolean expectedResult, double expectedBalance) {
        boolean result = checkingAccount.makeWithdrawal(amount);
        assertEquals(expectedResult, result);
        assertEquals(expectedBalance, checkingAccount.getCurrentBalance());
    }
}
