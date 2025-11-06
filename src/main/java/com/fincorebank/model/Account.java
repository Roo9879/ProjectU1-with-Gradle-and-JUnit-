package com.fincorebank.model;

public class Account {
    private String accountHolderName;
    private int accountNumber;
    private double currentBalance;
    // static field to auto increment account number every time account object is created
    private static int nextAccountNumber = 1;

    //Constructor
    public Account(String accountHolderName, double currentBalance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = nextAccountNumber;
        this.currentBalance = currentBalance;

        nextAccountNumber++; //increment account number
    }

    //getters and setters

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setAccountHolderName(String name) {
        this.accountHolderName = name;
    }

    protected void setCurrentBalance(double balance) {
        this.currentBalance = balance;
    }

    //com.fincorebank.model.Account methods: Deposit, withdraw, check balance
    public boolean makeDeposit(double amount) {
        if (amount <= 0 ) {
            System.out.println("Amount must be positive.");
            return false;
        }
        currentBalance += amount;
        System.out.println("\nDeposit successful!");
        System.out.println("Amount deposited: £" + amount);
        System.out.println("Your new balance is: £" + currentBalance);
        return true; //returns true if deposit successful so adds to transaction list
    }

    public boolean makeWithdrawal(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive");
            return false;
        } else if (amount > currentBalance) {
            System.out.println("Error: Insufficient funds");
            System.out.println("Attempted withdrawal: £" + amount);
            System.out.println("Your current balance is £" + currentBalance);
            return false;
        } else {
            currentBalance -= amount;
            System.out.println("\nWithdrawal successful!");
            System.out.println("Amount withdrawn: £" + amount);
            System.out.println("Your new balance is: £" + currentBalance);
            return true;
        }
    }

    public void checkBalance() {
        System.out.println("\n=== Account Balance ===");
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: £" + currentBalance);
    }

    public boolean transferTo(Account target, double amount) {
        if(amount <= 0) {
            System.out.println("Transfer amount must be positive");
            return false;
        }
        if (this.makeWithdrawal(amount)) {
            target.makeDeposit(amount);
            System.out.println("Transferred £" + amount + " from " + this.getAccountHolderName() + " to " + target.getAccountHolderName());
            return true;
        } else {
            System.out.println("Transfer failed: Insufficient funds");
            return false;
        }
    }
}