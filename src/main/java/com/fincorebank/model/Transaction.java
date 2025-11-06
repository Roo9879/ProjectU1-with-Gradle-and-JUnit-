package com.fincorebank.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction implements Comparable<Transaction>{
    private final String id;
    private final LocalDateTime dateTime;
    private final int accountNumber;
    private final double amount;
    private final String type; //deposit or withdrawal

    public Transaction(int accountNumber, double amount, String type) {
        this.id = UUID.randomUUID().toString();
        this.dateTime = LocalDateTime.now();
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(Transaction other) {
        return this.dateTime.compareTo(other.dateTime);
    }

    @Override
    public String toString() {
        return "[" + dateTime + "] " + type + " £" + amount + " (Account #" + accountNumber + ")";
    }

}
