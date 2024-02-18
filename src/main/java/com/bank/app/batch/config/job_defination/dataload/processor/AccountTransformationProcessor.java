package com.bank.app.batch.config.job_defination.dataload.processor;

import com.bank.app.domain.runtime.entities.Account;
import com.bank.app.domain.staging.entities.StgAccount;
import com.bank.app.domain.staging.repository.StgAccountRepo;
import com.bank.app.util.ValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component("accountTransformationProcessor")
public class AccountTransformationProcessor implements ItemProcessor<StgAccount, Account> {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccountTransformationProcessor.class);

    private final StgAccountRepo stgAccountRepo;

    public AccountTransformationProcessor(StgAccountRepo stgAccountRepo) {
        this.stgAccountRepo = stgAccountRepo;
    }

    @Override
    public Account process(@NonNull StgAccount stgAccount) throws Exception {
        LOGGER.info("Inside AccountTransformationProcessor.process() with Stg Account : {}",stgAccount);
        Account account=new Account();
        account.setAccountId(stgAccount.getAccountId());
        account.setOpeningDate(stgAccount.getOpeningDate());
        account.setClosingDate(stgAccount.getClosingDate());
        account.setStatus(stgAccount.getStatus());
        account.setBalance(0.0);
        LOGGER.info("After Transformation Account : {}",account);
        LOGGER.info("Change Stg Account Validation Status to Sync");
        statusChangeToSync(stgAccount);
        LOGGER.info("Stg Account Validation Status changed to Sync Account : {}",stgAccount);
        return account;
    }

    private void statusChangeToSync(StgAccount stgAccount){
        stgAccount.setValidationStatus(ValidationStatus.SYNC);
        stgAccountRepo.save(stgAccount);

    }
}
