package com.fincorebank.service;

import com.fincorebank.model.Account;

public interface AccountService {
    void deposit(Account account);
    void withdraw (Account account);
    void checkBalance (Account account);
    void deleteAccount(Account account, DataStore dataStore);
    void showSortedTransactions(Account account, String sortOption);
}