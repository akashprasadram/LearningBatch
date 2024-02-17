package com.bank.app.api.handler.staging;

import com.bank.app.api.staging.dto.StgAccountDTO;
import com.bank.app.domain.staging.entities.StgAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StgAccountConverter {

    private static final Logger LOGGER= LoggerFactory.getLogger(StgAccountConverter.class);
    public StgAccountDTO accountDTOConverter(StgAccount stgAccount){
        LOGGER.info("Inside accountDTOConverter()");
        StgAccountDTO stgAccountDTO =new StgAccountDTO();
        stgAccountDTO.setAccountId(stgAccount.getAccountId());
        stgAccountDTO.setStatus(stgAccount.getStatus());
        stgAccountDTO.setOpeningDate(stgAccount.getOpeningDate());
        stgAccountDTO.setClosingDate(stgAccount.getClosingDate());
        return stgAccountDTO;
    }

    public List<StgAccountDTO> accountDTOListConverter(List<StgAccount> stgAccounts){
        LOGGER.info("Inside accountDTOListConverter()");
        return stgAccounts.stream().map(this::accountDTOConverter).toList();
    }

    public StgAccount accountConverter(StgAccountDTO stgAccountDTO){
        LOGGER.info("Inside accountConverter()");
        StgAccount stgAccount =new StgAccount();
        stgAccount.setAccountId(stgAccountDTO.getAccountId());
        stgAccount.setStatus(stgAccountDTO.getStatus());
        stgAccount.setOpeningDate(stgAccountDTO.getOpeningDate());
        stgAccount.setClosingDate(stgAccountDTO.getClosingDate());
        return stgAccount;
    }

    public List<StgAccount> accountListConverter(List<StgAccountDTO> stgAccountDTOS){
        LOGGER.info("Inside accountListConverter()");
        return stgAccountDTOS.stream().map(this::accountConverter).toList();
    }
}
