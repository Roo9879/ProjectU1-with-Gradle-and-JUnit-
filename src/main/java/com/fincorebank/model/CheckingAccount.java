package com.fincorebank.model;

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountHolderName, double currentBalance, double overdraftLimit) {
        super(accountHolderName, currentBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double limit) {
        this.overdraftLimit = limit;
    }

    @Override //override withdrawal to allow overdraft
    public boolean makeWithdrawal(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be postive");
            return false;
        } else if (amount > getCurrentBalance() + overdraftLimit) {
            System.out.println("Exceeded overdraft limit! Max allowed: £" + (getCurrentBalance() + overdraftLimit));
            return false;
        } else {
            //allow balance to go -ve up t overdraft limit
            double newBalance = getCurrentBalance() - amount;
            setCurrentBalance(newBalance);
            System.out.println("\nWithdrawal successful!");
            System.out.println("Amount withdrawn: £" + amount);
            System.out.println("Your new balance is: £" + newBalance);
            return true;
        }
    }
}
