package com.fincorebank.service;

import com.fincorebank.model.Account;
import com.fincorebank.model.Transaction;
import java.util.*;

public class InMemoryDataStore implements DataStore{
    private Map<Integer, Account> accounts;
    private Map<Integer, List<Transaction>> transactions;

    public InMemoryDataStore() {
        this.accounts = new HashMap<>();
        this.transactions = new HashMap<>();
    }

    @Override
    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    @Override
    public Account findAccountByNumber(int accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public Account findAccountByName(String accountHolderName) {
        for (Account acc : accounts.values()) {
            if (acc.getAccountHolderName().equalsIgnoreCase(accountHolderName)) {
                return acc;
            }
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values()); //creates a new list of accounts, can use for sorting and displaying. copy of account data
    }

    @Override
    public void updateAccount(Account account) {
        //replace existing entry (if exits)
        accounts.put(account.getAccountNumber(), account);
    }


    @Override
    public void deleteAccount(int accountNumber) {
        accounts.remove(accountNumber);
        transactions.remove(accountNumber); // removes transaction history for that account
    }

    //transaction specific methods:
    public void addTransaction(int accountNumber, Transaction transaction) {
        transactions.computeIfAbsent(accountNumber, k -> new ArrayList<>()).add(transaction);
    }

    public List<Transaction> getTransactionsForAccount(int accountNumber) {
        return new ArrayList<>(transactions.getOrDefault(accountNumber, new ArrayList<>()));
    }

    //get transactions across all accounts, global
    public List<Transaction> getAllTransactions() {
        List<Transaction> all = new ArrayList<>();
        for (List<Transaction> list : transactions.values()) { //returns all lists stored in the map (for each account)
            all.addAll(list); //adds all transactions from each account to new list
        }
        return all;
    }

    @Override
    //get transactions for an account sorted by given comparator
    public List<Transaction> getSortedTransactions(int accountNumber, Comparator<Transaction> comparator) {
        List<Transaction> list = getTransactionsForAccount(accountNumber); //safe copy of transaction list for account

        list.sort(comparator); //sort list using comparator provided
        return list;
    }
}
