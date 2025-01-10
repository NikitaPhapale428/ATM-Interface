package com.atm;

public class Main_Atm_Class {

	public static void main(String[] args) {
		Bank bank = new Bank();
		bank.addAccountHolder("Nikita", "2703", 1000.0);
		bank.addAccountHolder("Swati", "2607", 500.0);

		ATM atm = new ATM(bank);
		atm.start();
	}
}
