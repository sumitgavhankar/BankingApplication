package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.model.Customer;

public interface AccountService {

	Account createAccount(int accountNumber, int amount, Customer customer) throws InvalidAccountNumberException, InsufficientInitialBalanceException;

	int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException, InsufficientBalanceException;

	int withdrawAmount(int accountNumber, int withdrawAmount)
			throws InvalidAccountNumberException, InsufficientBalanceException;

	Account fundTransfer(int accountNum1, int accountNum2, int amount) throws InvalidAccountNumberException, InsufficientBalanceException;

	int showBalance(int accountNumber) throws InvalidAccountNumberException;

}