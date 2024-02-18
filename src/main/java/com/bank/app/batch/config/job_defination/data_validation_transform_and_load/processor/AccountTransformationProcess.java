package com.bank.app.batch.config.job_defination.data_validation_transform_and_load.processor;

import com.bank.app.domain.runtime.entities.Account;
import com.bank.app.domain.staging.entities.StgAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component("accountTransformationProcess")
public class AccountTransformationProcess implements ItemProcessor<StgAccount, Account> {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccountTransformationProcess.class);
    @Override
    public Account process(@NonNull StgAccount stgAccount) throws Exception {

        LOGGER.info("Inside StgAccount() with Stg Account : {}",stgAccount);
        Account account=new Account();
        account.setAccountId(stgAccount.getAccountId());
        account.setOpeningDate(stgAccount.getOpeningDate());
        account.setClosingDate(stgAccount.getClosingDate());
        account.setStatus(stgAccount.getStatus());
        account.setBalance(0.0);
        return account;
    }
}
