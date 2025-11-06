package com.fincorebank.model;

public class SavingsAccount extends Account {
    private double interestRate;

    //constructor using super
    public SavingsAccount(String accountHolderName, double currentBalance, double interestRate) {
        super(accountHolderName, currentBalance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double rate) {
        this.interestRate = rate;
    }

    public void addInterest() {
        double interest = getCurrentBalance() * interestRate / 100;
        makeDeposit(interest);
        System.out.println("Interest of £" + interest + " added. New balance: £" + getCurrentBalance());
    }

    @Override
    public boolean makeWithdrawal(double amount) {
        //limit withdrawals for savings account
        if (amount > 1000) {
            System.out.println("Withdrawal limit exceeded for Savings Account.");
            return false;
        } else {
           return super.makeWithdrawal(amount);
        }
    }
}
