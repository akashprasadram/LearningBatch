package com.bank.app.batch.config.job_defination.data_validation.processor;

import com.bank.app.domain.common.error.exceptions.StgRelationshipValidationError;
import com.bank.app.domain.staging.entities.StgAccount;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.staging.entities.StgRelationship;
import com.bank.app.domain.staging.repository.StgAccountRepo;
import com.bank.app.domain.staging.repository.StgCustomerRepo;
import com.bank.app.util.ValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("stgRelationshipValidationProcess")
public class StgRelationshipValidationProcess implements ItemProcessor<StgRelationship, StgRelationship> {

    private static final Logger LOGGER= LoggerFactory.getLogger(StgRelationshipValidationProcess.class);
    private final StgAccountRepo stgAccountRepo;
    private final StgCustomerRepo stgCustomerRepo;

    public StgRelationshipValidationProcess(StgAccountRepo stgAccountRepo, StgCustomerRepo stgCustomerRepo) {

        this.stgAccountRepo = stgAccountRepo;
        this.stgCustomerRepo = stgCustomerRepo;
    }

    @Override
    public StgRelationship process(@NonNull StgRelationship stgRelationship) throws Exception {
        LOGGER.info("Inside StgRelationshipValidationProcess.process() with Stg Relationship : {}",stgRelationship);
        validateRelationship(stgRelationship);
        return stgRelationship;
    }

    public void validateRelationship(StgRelationship stgRelationship) throws StgRelationshipValidationError {
        LOGGER.info("Inside validateRelationship() with Stg Relationship : {}",stgRelationship);

        List<String> comments=new ArrayList<>();

        StgAccount account=stgRelationship.getAccountId();
        StgCustomer customer=stgRelationship.getCustomerId();
        if(account.getValidationStatus().equals(ValidationStatus.FAIL)){
            comments.add("Account Validation Failed");
        }
        if(customer.getValidationStatus().equals(ValidationStatus.FAIL)){
            comments.add("Customer Validation Failed");
        }

        if(!comments.isEmpty()) {
            LOGGER.info("Validation Error with Comment : {}" , comments);
            account.setValidationStatus(ValidationStatus.FAIL);
            customer.setValidationStatus(ValidationStatus.FAIL);
            stgAccountRepo.save(account);
            stgCustomerRepo.save(customer);
            stgRelationship.setComment(comments.toString());
            stgRelationship.setValidationStatus(ValidationStatus.FAIL);
            //throw new StgRelationshipValidationError("Relationship validation failed with Errors : "+comments.toString());
        }
        else {
            stgRelationship.setValidationStatus(ValidationStatus.PASS);
        }

    }


}
