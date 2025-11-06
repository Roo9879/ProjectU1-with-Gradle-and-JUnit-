package com.fincorebank.cli;

import com.fincorebank.model.*;
import com.fincorebank.service.*;

import java.security.Provider;
import java.util.Scanner;

public class FinCoreCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // initialise DataStore and ServiceHandler
        DataStore dataStore = new InMemoryDataStore();
        AccountService handler = new ServiceHandler(scanner, dataStore);

        //creating bank accounts
        dataStore.addAccount(new Account("Jane Doe", 1000));//normal account
        dataStore.addAccount(new CheckingAccount("John Doe", 500, 200)); // checking account
        dataStore.addAccount(new SavingsAccount("Jacob Doe", 2000, 1.25));//savings account

        System.out.println("\nWelcome to FinCore CLI Banking!");

        //let user select account by name
        Account currentAccount = null;
        while (currentAccount == null) {
            System.out.print("\nEnter account holder name to access your account: ");
            String name = scanner.nextLine();
            currentAccount = dataStore.findAccountByName(name);
            if (currentAccount == null) {
                System.out.println("Account not found. Please try again.");
            }
        }

        //Display basic account info
        System.out.println("\nAccount Holder: " + currentAccount.getAccountHolderName());
        System.out.println("Account Number: " + currentAccount.getAccountNumber());
        System.out.println("Initial Balance: £" + currentAccount.getCurrentBalance());

        //main menu display
        String option;

        do {
            System.out.println("\n=== FinCore CLI Banking Menu ===");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.println("5. Delete Account");
            System.out.println("6. View Transaction History (sorted)");
            System.out.println("7. Transfer funds between accounts");
            System.out.println("Please select an option (input number 1-7): ");
            option = scanner.nextLine();

            switch (option) {
                case "1": //deposit
                    handler.deposit(currentAccount);
                    break;
                case "2": //withdraw
                    handler.withdraw(currentAccount);
                    break;
                case "3": //check balance
                    handler.checkBalance(currentAccount);
                    break;
                case "4": //exit
                    System.out.println("\nThank you for using FinCore CLI Banking!");
                    break;
                case "5": //delete account
                    handler.deleteAccount(currentAccount, dataStore);
                    System.out.println("\n Thank you for using FinCore CLI Banking! Goodbye!");
                    option = "4"; //breaks loop and exits
                    break;
                case "6":
                    System.out.println("\nSort transactions by:");
                    System.out.println("1. Chronological (oldest first)");
                    System.out.println("2. Reverse chronological (newest first)");
                    System.out.println("3. Amount (ascending)");
                    System.out.println("4. Type (alphabetical)");
                    System.out.print("Choose an option: ");
                    String sortChoice = scanner.nextLine();

                    handler.showSortedTransactions(currentAccount, sortChoice);
                    break;
                case "7":
                    System.out.print("Enter sender's account name: ");
                    String fromName = scanner.nextLine();
                    System.out.print("Enter receiver's account name: ");
                    String toName = scanner.nextLine();
                    System.out.print("Enter transfer amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    if (handler.transferFunds(fromName, toName, amount)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed. Please check details and balance.");
                    }
                    break;
                default: //invalid choices
                    System.out.println("Invalid choice. Please try again and enter a number between 1 and 7.");
                    break;
            }
        } while (!option.equals("4"));
        scanner.close();
    }
}