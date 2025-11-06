package com.fincorebank.service;

import com.fincorebank.model.Account;
import com.fincorebank.model.Transaction;

import java.util.Comparator;
import java.util.List;
//interface for CRUD implementation
public interface DataStore {
    void addAccount(Account account); //create
    Account findAccountByNumber(int accountNumber); //read
    Account findAccountByName(String name); //read
    List<Account> getAllAccounts(); //read all
    void updateAccount(Account account); //update
    void deleteAccount(int accountNumber); //delete

    //transaction methods
    void addTransaction(int accountNumber, Transaction transaction);
    List<Transaction> getTransactionsForAccount(int accountNumber);
    List<Transaction> getAllTransactions();
    List<Transaction> getSortedTransactions(int accountNumber, Comparator<Transaction> comparator);
}