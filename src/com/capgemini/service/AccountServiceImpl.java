package com.capgemini.service;

import java.util.Set;

import com.capgemini.exceptions.ExceptionConstants;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.model.Customer;
import com.capgemini.repository.AccountRepository;
import com.capgemini.repository.AccountRepositoryImpl;

public class AccountServiceImpl implements AccountService {

	


	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}

	public AccountServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	AccountRepository accountRepository = new AccountRepositoryImpl();
	

	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int, com.capgemini.model.Customer)
	 */
	@Override
	public Account createAccount(int accountNumber, int amount, Customer customer) throws InvalidAccountNumberException, InsufficientInitialBalanceException {
		Set<Account>accounts=accountRepository.findAllAccounts();
		
		for (Account account : accounts) {
			if(account==null){
				throw new InvalidAccountNumberException(ExceptionConstants.ACCOUNT_DETAILS_INVALID);
			}
			if (accountNumber==account.getAccountNumber())
				throw new InvalidAccountNumberException(ExceptionConstants.ACCOUNT_ALREADY_EXISTS);
			if(amount<BankingContants.INITIAL_BALANCE){
				throw new InsufficientInitialBalanceException(ExceptionConstants.REQUIRED_INITIAL_AMOUNT);
			}
			
		}
		if(customer==null){
			throw new InvalidAccountNumberException(ExceptionConstants.ACCOUNT_DETAILS_INVALID);
		}
		Account account = new Account(accountNumber, amount, customer);
		accountRepository.saveAccount(account);
		System.out.println("Account created successfully:["+accountNumber+ "] Balance:"+amount );
		return account;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#depositAmount(int, int)
	 */
	@Override
	public int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException, InsufficientBalanceException {
		Account account = accountRepository.searchAccount(accountNumber);
		if(amount<0){
			throw new InsufficientBalanceException(ExceptionConstants.NO_NEGATIVE_DEPOSIT);
		}
		account.setAmount(account.getAmount() + amount);
		System.out.println("Acount:"+accountNumber+" is credited with Rs."+amount +"[Balance:"+account.getAmount()+"]");
		return account.getAmount();

	}

	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#withdrawAmount(int, int)
	 */
	@Override
	public synchronized int  withdrawAmount(int accountNumber, int withdrawAmount)
			throws InvalidAccountNumberException, InsufficientBalanceException {
		Account account = accountRepository.searchAccount(accountNumber);
		if (withdrawAmount > account.getAmount()) {
			throw new InsufficientBalanceException(ExceptionConstants.WITHDRAW_AMOUNT_HIGH);
		}else if(withdrawAmount<0){
			throw new InsufficientBalanceException(ExceptionConstants.WITHDRAW_AMOUNT_INVALID);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		account.setAmount(account.getAmount() - withdrawAmount);
		System.out.println("Acount:"+accountNumber+" is debited with Rs."+withdrawAmount +"[Balance:"+account.getAmount()+"]");
		return account.getAmount();

	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#fundTransfer(int, int, int)
	 */
	@Override
	public synchronized Account fundTransfer(int accountNum1, int accountNum2, int amount) throws InvalidAccountNumberException, InsufficientBalanceException
	{
		System.out.println("Fund Transfer initiated...");
		Account account1=new Account();
		account1.setAccountNumber(accountNum1);
		account1.setAmount(withdrawAmount(accountNum1, amount));
		
		Account account2=new Account();
		account2.setAccountNumber(accountNum2);
		account2.setAmount(depositAmount(accountNum2, amount));
		
		accountRepository.updateAccount(account1);
		accountRepository.updateAccount(account2);
		System.out.println("Fund Transfer Completed Successfully...");
		return account2;
	}
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#showBalance(int)
	 */
	@Override
	public int showBalance(int accountNumber) throws InvalidAccountNumberException {
		Account account = accountRepository.searchAccount(accountNumber);
		System.out.println("Balance for ["+accountNumber+"] is Rs."+account.getAmount() );
		return account.getAmount();
	}
	
	
}
