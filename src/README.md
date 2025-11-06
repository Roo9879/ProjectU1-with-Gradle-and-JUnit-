Project Overview:
* This repo is for my JSE Project 1: Building and Testing in Java. I'm are building a banking management application
* My final deliverable will be a fully functional application with comprehensive documentation and a test suite, suitable for enterprise organisations, providing practical product delivery experience

* Task 1: Setting up Java environment
* Task 2: Implement logic
* Task 3: Re-architect your application
* Task 4: Organise your application
* Task 5: Enhance your application
* Task 6: Develop a test suite

=== FinCore CLI Banking Application ===

Application Overview:
* FinCore CLI is a Java console-based application that allows users to perform essential financial opertions in a simple, text-based interface.

Features:
* Deposit funds
* Withdraw funds
* Check account balance
* Cancel transactions
* Input validation and error handling
* Delete accounts
* Full CRUD support via a dedicated data store
* Modular structure with interfaces for flexibility

Project Structure:
src/
└── com/
    └── fincorebank/
        ├── cli/
        │   └── FinCoreCLI.java               # Command-line interface (main entry point)
        │
        ├── model/
        │   ├── Account.java                  # Base account class
        │   ├── CheckingAccount.java          # Supports overdrafts
        │   ├── SavingsAccount.java           # Supports interest rates
        │
        ├── service/
        │   ├── AccountService.java           # Interface for account operations
        │   ├── ServiceHandler.java           # Implements AccountService logic
        │   ├── DataStore.java                # Interface for CRUD operations
        │   ├── InMemoryDataStore.java        # In-memory implementation of DataStore
        │
        └── module-info.java                  # Defines module exports and dependencies

How To Run:
1) Clone repository
2) Navigate to project folder
3) Compile
4) Run

How It Works:
* When the program starts, it initialises a few example accounts in memory
* The user selects their account by name
* Through a simple menu, they can deposit, withdraw or check their balance
* CRUD functionality allows for account deletion, after which the program exits gracefully

Development Environment Setup:
* JDK : Eclipse 25 JDK
* Language : Java
* IDE : IntelliJ IDEA
* Build tools: None but should use Gradle in future
* Dependencies: JUnit to test code


  

Requirements:
* Java JDK 8 or later

Author:
Rosie Curry 
