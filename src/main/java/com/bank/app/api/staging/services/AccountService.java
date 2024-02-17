package com.bank.app.api.staging.services;

import java.util.List;

import com.bank.app.api.staging.dto.AccountDTO;
import com.bank.app.domain.staging.entities.Account;
import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;

public interface AccountService {
	public List<AccountDTO> getAccounts();
	public AccountDTO getAccountById(Long id) throws DataNotFoundException;
	public AccountDTO createAccount(Account account) throws DataCreationError;
	public List<AccountDTO> createAccounts(List<Account> accounts) throws DataCreationError;
	
}
