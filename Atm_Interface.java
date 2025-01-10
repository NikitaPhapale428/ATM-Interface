package com.atm;

	import java.util.ArrayList;
	import java.util.Scanner;


	class AccountHolder {
	    private String userId;
	    private String userPin;

	    public AccountHolder(String userId, String userPin) {
	        this.userId = userId;
	        this.userPin = userPin;
	    }

	    public String getUserId() {
	        return userId;
	    }

	    public String getUserPin() {
	        return userPin;
	    }
	}

	
	class Account {
	    private double balance;
	    private ArrayList<BankTransaction> transactionHistory;

	    public Account(double initialBalance) {
	        this.balance = initialBalance;
	        this.transactionHistory = new ArrayList<>();
	    }

	    public double getBalance() {
	        return balance;
	    }

	    public void deposit(double amount) {
	        balance += amount;
	        transactionHistory.add(new BankTransaction("Deposit", amount, balance));
	    }

	    public boolean withdraw(double amount) {
	        if (amount > balance) {
	            return false;
	        }
	        balance -= amount;
	        transactionHistory.add(new BankTransaction("Withdraw", amount, balance));
	        return true;
	    }

	    public void transfer(Account recipient, double amount) {
	        if (withdraw(amount)) {
	            recipient.deposit(amount);
	            transactionHistory.add(new BankTransaction("Transfer", amount, balance));
	        }
	    }

	    public void showTransactionHistory() {
	        if (transactionHistory.isEmpty()) {
	            System.out.println("No transactions found.");
	            return;
	        }
	        for (BankTransaction transaction : transactionHistory) {
	            System.out.println(transaction);
	        }
	    }
	}

	
	class BankTransaction {
	    private String type;
	    private double amount;
	    private double balanceAfter;

	    public BankTransaction(String type, double amount, double balanceAfter) {
	        this.type = type;
	        this.amount = amount;
	        this.balanceAfter = balanceAfter;
	    }

	    @Override
	    public String toString() {
	        return type + " of $" + amount + " | Balance after: $" + balanceAfter;
	    }
	}

	
	class Bank {
	    private ArrayList<AccountHolder> accountHolders;
	    private ArrayList<Account> accounts;

	    public Bank() {
	        accountHolders = new ArrayList<>();
	        accounts = new ArrayList<>();
	    }

	    public void addAccountHolder(String userId, String userPin, double initialBalance) {
	        accountHolders.add(new AccountHolder(userId, userPin));
	        accounts.add(new Account(initialBalance));
	    }

	    public int validateUser(String userId, String userPin) {
	        for (int i = 0; i < accountHolders.size(); i++) {
	            AccountHolder holder = accountHolders.get(i);
	            if (holder.getUserId().equals(userId) && holder.getUserPin().equals(userPin)) {
	                return i;
	            }
	        }
	        return -1;
	    }

	    public Account getAccount(int index) {
	        return accounts.get(index);
	    }
	}


	 class ATM {
	    private Bank bank;

	    public ATM(Bank bank) {
	        this.bank = bank;
	    }

	    public void start() {
	        Scanner scanner = new Scanner(System.in);

	        System.out.println("Welcome to the ATM");
	        System.out.print("Enter User ID: ");
	        String userId = scanner.nextLine();
	        System.out.print("Enter PIN: ");
	        String userPin = scanner.nextLine();

	        int userIndex = bank.validateUser(userId, userPin);
	        if (userIndex == -1) {
	            System.out.println("Invalid User ID or PIN.");
	            return;
	        }

	        Account account = bank.getAccount(userIndex);

	        while (true) {
	            System.out.println("\nATM Menu:");
	            System.out.println("1. Show Transaction History");
	            System.out.println("2. Withdraw");
	            System.out.println("3. Deposit");
	            System.out.println("4. Transfer");
	            System.out.println("5. Quit");
	            System.out.print("Choose an option: ");
	            int choice = scanner.nextInt();

	            switch (choice) {
	                case 1:
	                    account.showTransactionHistory();
	                    break;
	                case 2:
	                    System.out.print("Enter amount to withdraw: ");
	                    double withdrawAmount = scanner.nextDouble();
	                    if (account.withdraw(withdrawAmount)) {
	                        System.out.println("Withdraw successful.");
	                    } else {
	                        System.out.println("Insufficient balance.");
	                    }
	                    break;
	                case 3:
	                    System.out.print("Enter amount to deposit: ");
	                    double depositAmount = scanner.nextDouble();
	                    account.deposit(depositAmount);
	                    System.out.println("Deposit successful.");
	                    break;
	                case 4:
	                    System.out.print("Enter recipient User ID: ");
	                    String recipientId = scanner.next();
	                    System.out.print("Enter amount to transfer: ");
	                    double transferAmount = scanner.nextDouble();
	                    int recipientIndex = bank.validateUser(recipientId, "");
	                    if (recipientIndex != -1) {
	                        account.transfer(bank.getAccount(recipientIndex), transferAmount);
	                        System.out.println("Transfer successful.");
	                    } else {
	                        System.out.println("Invalid recipient.");
	                    }
	                    break;
	                case 5:
	                    System.out.println("Thank you for using the ATM.");
	                    return;
	                default:
	                    System.out.println("Invalid option. Try again.");
	            }
	        }
	    }
	}
