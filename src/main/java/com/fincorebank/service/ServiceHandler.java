package com.fincorebank.service;

import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import com.fincorebank.model.*;

public class ServiceHandler implements AccountService {
    private Scanner scanner;
    private DataStore dataStore;

    //comparators
    public final Comparator<Transaction> byDate = Comparator.naturalOrder();
    public final Comparator<Transaction> reverseDate = Comparator.comparing(Transaction::getDateTime).reversed();
    public final Comparator<Transaction> byAmount = Comparator.comparing(Transaction::getAmount);
    public final Comparator<Transaction> byType = Comparator.comparing(Transaction::getType);

    //constructor
    public ServiceHandler(Scanner scanner, DataStore dataStore) {
        this.scanner = scanner;
        this.dataStore = dataStore;
    }

    @Override
    public void deposit(Account account) {
        System.out.print("\nEnter your amount to deposit (or 'x' to cancel) : £");
        String depositInput = scanner.nextLine();
        if (!depositInput.equalsIgnoreCase("x")) {
            try {
                double depositAmount = Double.parseDouble(depositInput);
                boolean success = account.makeDeposit(depositAmount);
                if (success && dataStore instanceof InMemoryDataStore) {
                    ((InMemoryDataStore) dataStore).addTransaction(
                            account.getAccountNumber(),
                            new Transaction(account.getAccountNumber(), depositAmount, "Deposit")
                    );
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number");
            }
        } else {
            System.out.println("\nDeposit cancelled, returning to main menu.");
        }
    }

    @Override
    public void withdraw(Account account) {
        System.out.print("\nEnter your amount to withdraw (or 'x' to cancel): £");
        String withdrawalInput = scanner.nextLine();
        if (!withdrawalInput.equalsIgnoreCase("x")) {
            try {
                double withdrawalAmount = Double.parseDouble(withdrawalInput);
                boolean success = account.makeWithdrawal(withdrawalAmount);
                if (success && dataStore instanceof InMemoryDataStore) {
                    ((InMemoryDataStore) dataStore).addTransaction(
                            account.getAccountNumber(),
                            new Transaction(account.getAccountNumber(), withdrawalAmount, "Withdrawal")
                    );
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number");
            }
        } else {
            System.out.println("\nWithdrawal cancelled, returning to main menu");
        }
    }

    @Override
    public void checkBalance(Account account) {
        System.out.println("\n=== Account Balance ===");
        System.out.println("Account Holder: " + account.getAccountHolderName());
        System.out.println("Current Balance: " + account.getCurrentBalance());

        // show overdraft for checking account
        if (account instanceof CheckingAccount) {
            CheckingAccount chk = (CheckingAccount) account;
            System.out.println("Overdraft limit: £" + chk.getOverdraftLimit());
            System.out.println("Available Funds: £" + (account.getCurrentBalance() + chk.getOverdraftLimit()));
        }

        //show interest rate for savings account
        if (account instanceof SavingsAccount) {
            SavingsAccount sav = (SavingsAccount) account;
            System.out.println("Interest Rate: " + sav.getInterestRate() + "%");
        }
    }

    @Override
    public void deleteAccount(Account account, DataStore dataStore) {
        System.out.println("\n Are you sure you want to delete your account? (y/n): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            dataStore.deleteAccount(account.getAccountNumber());
            System.out.println("Account deleted successfully");
        } else {
            System.out.println("Account deletion cancelled");
        }
    }


    @Override
    public void showSortedTransactions(Account account, String sortOption) {
        //decide which comparator to use based on user input
        Comparator<Transaction> comparator;

        switch(sortOption) {
            case "1":
                comparator = this.byDate; //chronological
                break;
            case "2":
                comparator = this.reverseDate; //reverse chronological
                break;
            case "3":
                comparator = this.byAmount; //amount ascending
                break;
            case "4":
                comparator = this.byType; //type alphabetical
                break;
            default:
                comparator = this.byDate; //default to chronological
                break;
        }

        //get sorted list of transactions from DataStore
        List<Transaction> sorted = dataStore.getSortedTransactions(account.getAccountNumber(), comparator);

        if(sorted.isEmpty()) {
            System.out.println("\n No transaction history for this account");
            return;
        }

        System.out.println("\n === Transaction History ===");
        sorted.forEach(System.out::println);
    }

    @Override
    public boolean transferFunds(String fromName, String toName, double amount) {
        Account fromAccount = dataStore.findAccountByName(fromName);
        Account toAccount = dataStore.findAccountByName(toName);

        if (fromAccount == null || toAccount == null) {
            System.out.println("One or both accounts not found");
            return false;
        }
        if (fromAccount.equals(toAccount)) {
            System.out.println("Cannot transfer to the same account");
            return false;
        }

        boolean success = fromAccount.transferTo(toAccount, amount);

        if(success) {
            dataStore.updateAccount(fromAccount);
            dataStore.updateAccount(toAccount);

            Transaction transferOut = new Transaction(fromAccount.getAccountNumber(), -amount, "Transfer to " + toAccount.getAccountHolderName());
            Transaction transferIn = new Transaction(toAccount.getAccountNumber(), amount, "Transfer from " + fromAccount.getAccountHolderName());
            dataStore.addTransaction(fromAccount.getAccountNumber(), transferOut);
            dataStore.addTransaction(toAccount.getAccountNumber(), transferIn);
        }
        return success;
    }
}
