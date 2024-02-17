package com.bank.app.api.handler.staging;

import com.bank.app.api.staging.dto.AccountDTO;
import com.bank.app.domain.staging.entities.StgAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountConverter {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccountConverter.class);
    public AccountDTO accountDTOConverter(StgAccount stgAccount){
        LOGGER.info("Inside accountDTOConverter()");
        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setAId(stgAccount.getAId());
        accountDTO.setStatus(stgAccount.getStatus());
        accountDTO.setOpeningDate(stgAccount.getOpeningDate());
        accountDTO.setClosingDate(stgAccount.getClosingDate());
        return accountDTO;
    }

    public List<AccountDTO> accountDTOListConverter(List<StgAccount> stgAccounts){
        LOGGER.info("Inside accountDTOListConverter()");
        return stgAccounts.stream().map(this::accountDTOConverter).toList();
    }

    public StgAccount accountConverter(AccountDTO accountDTO){
        LOGGER.info("Inside accountConverter()");
        StgAccount stgAccount =new StgAccount();
        stgAccount.setAId(accountDTO.getAId());
        stgAccount.setStatus(accountDTO.getStatus());
        stgAccount.setOpeningDate(accountDTO.getOpeningDate());
        stgAccount.setClosingDate(accountDTO.getClosingDate());
        return stgAccount;
    }

    public List<StgAccount> accountListConverter(List<AccountDTO> accountDTOs){
        LOGGER.info("Inside accountListConverter()");
        return accountDTOs.stream().map(this::accountConverter).toList();
    }
}
