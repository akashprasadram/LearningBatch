package com.bank.app.batch.config.job_defination.data_validation.processor;

import com.bank.app.domain.common.error.exceptions.StgAccountValidationError;
import com.bank.app.domain.staging.entities.StgAccount;

import com.bank.app.domain.staging.entities.StgRelationship;
import com.bank.app.domain.staging.repository.StgRelationshipRepo;
import com.bank.app.util.AccountStatus;
import com.bank.app.util.ValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("stgAccountValidationProcess")
public class StgAccountValidationProcess implements ItemProcessor<StgAccount, StgAccount> {

    private static final Logger LOGGER= LoggerFactory.getLogger(StgAccountValidationProcess.class);

    private final StgRelationshipRepo stgRelationshipRepo;

    public StgAccountValidationProcess(StgRelationshipRepo stgRelationshipRepo) {
        this.stgRelationshipRepo = stgRelationshipRepo;
    }


    @Override
    public StgAccount process(@NonNull StgAccount stgAccount) throws Exception {
        LOGGER.info("Inside AccountValidationProcess.process() with Stg Account : {}",stgAccount);
        validateAccount(stgAccount);
        LOGGER.info("After Validation Stg Account : {}",stgAccount);
        return stgAccount;
    }
    public synchronized void validateAccount(StgAccount stgAccount) throws StgAccountValidationError {
        LOGGER.info("Inside validateAccount() with Stg Account : {}",stgAccount);
        List<String> comments=new ArrayList<>();

        relationshipCheckValidator(stgAccount, comments);
        closingDataValidator(stgAccount, comments);

        if(!comments.isEmpty()) {
            LOGGER.info("Validation Error with Comment : {}" , comments);
            stgAccount.setComment(comments.toString());
            stgAccount.setValidationStatus(ValidationStatus.FAIL);
            //throw new StgAccountValidationError("Account validation failed with Errors : "+comments.toString());
        }
        else {
            stgAccount.setValidationStatus(ValidationStatus.PASS);
        }
    }

    private static void closingDataValidator(StgAccount stgAccount, List<String> comments) {
        if(stgAccount.getStatus().equals(AccountStatus.CLOSE) && stgAccount.getClosingDate()==null){
            comments.add("Account is closed but closing date is not mentioned");
        }
    }

    private void relationshipCheckValidator(StgAccount stgAccount, List<String> comments) {
        List<StgRelationship> resultStgAccount  = stgRelationshipRepo.findByAccountId(stgAccount);
        if(resultStgAccount.isEmpty()){
            comments.add("Account is not associated with any customer");
        }
    }
}
