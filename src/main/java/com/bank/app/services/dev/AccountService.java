package com.bank.app.services.dev;

import java.util.List;

import com.bank.app.entities.dev.Account;
import com.bank.app.exceptions.DataCreationError;
import com.bank.app.exceptions.DataNotFoundException;

public interface AccountService {
	public List<Account> getAccounts();
	public Account getAccountById(Long id) throws DataNotFoundException;
	public Account createAccount(Account account) throws DataCreationError;
	public List<Account> createAccounts(List<Account> accounts) throws DataCreationError;
	
}
