package com.capgemini.view;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Customer;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
import com.capgemini.service.RandomAccountNumberGenerator;

public class MainBankApp extends Thread{

	public MainBankApp() {
		// TODO Auto-generated constructor stub
	}

	static AccountService accountService = new AccountServiceImpl();
	static RandomAccountNumberGenerator randomAccGenerator  = new RandomAccountNumberGenerator();

	public static void main(String[] args) throws InvalidAccountNumberException, InsufficientBalanceException, InsufficientInitialBalanceException {
		// TODO Auto-generated method stub

		
		/* Customer customer1= new Customer("Poonam","Kumari"); Customer
		 customer2= new Customer("Rama","Devi");
		 //accountService.createAccount(randomAccGenerator.generateAccountNumber(), 50000, customer1);
		 //accountService.createAccount(randomAccGenerator.generateAccountNumber(), 60000, customer2);
		 accountService.createAccount(4444, 50000, customer1);
		 accountService.createAccount(5555, 60000, customer2);
		 accountService.depositAmount(4444, 500);
		 accountService.withdrawAmount(5555, 500);
		 accountService.fundTransfer(5555, 4444, 100);
		 accountService.showBalance(4444); 
		 accountService.showBalance(5555);
		 accountService.showBalance(1111); 
		 accountService.showBalance(2222);*/
		 
		Customer customer = new Customer("Bittu", "Devi");
		accountService.createAccount(6666, 50000, customer);
		System.out.println(Thread.currentThread().getName());
		
		MainBankApp m1 = new MainBankApp();
		MainBankApp m2 = new MainBankApp();
		System.out.println("Test");
		m1.start();
		m2.start();
		
			
	}
	
	public void run(){
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("In run...");
			accountService.withdrawAmount(6666, 30000);
		} catch (InvalidAccountNumberException | InsufficientBalanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
