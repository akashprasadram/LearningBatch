package com.bank.app.api.handler.staging;

import com.bank.app.api.staging.dto.AccountDTO;
import com.bank.app.api.staging.services.CustomerServiceImpl;
import com.bank.app.domain.staging.entities.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountConverter {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccountConverter.class);
    public AccountDTO accountDTOConverter(Account account){
        LOGGER.info("Inside accountDTOConverter()");
        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setAId(account.getAId());
        accountDTO.setStatus(account.getStatus());
        accountDTO.setOpeningDate(account.getOpeningDate());
        accountDTO.setClosingDate(account.getClosingDate());
        return accountDTO;
    }

    public List<AccountDTO> accountDTOListConverter(List<Account> accounts){
        LOGGER.info("Inside accountDTOListConverter()");
        return accounts.stream().map(this::accountDTOConverter).toList();
    }

    public Account accountConverter(AccountDTO accountDTO){
        LOGGER.info("Inside accountConverter()");
        Account account=new Account();
        account.setAId(accountDTO.getAId());
        account.setStatus(accountDTO.getStatus());
        account.setOpeningDate(accountDTO.getOpeningDate());
        account.setClosingDate(accountDTO.getClosingDate());
        return account;
    }

    public List<Account> accountListConverter(List<AccountDTO> accountDTOs){
        LOGGER.info("Inside accountListConverter()");
        return accountDTOs.stream().map(this::accountConverter).toList();
    }
}
