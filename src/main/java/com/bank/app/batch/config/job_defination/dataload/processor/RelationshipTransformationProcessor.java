package com.bank.app.batch.config.job_defination.dataload.processor;

import com.bank.app.domain.common.error.exceptions.RelationshipLoadError;
import com.bank.app.domain.runtime.entities.Account;
import com.bank.app.domain.runtime.entities.Customer;
import com.bank.app.domain.runtime.entities.Relationship;
import com.bank.app.domain.runtime.repository.AccountRepo;
import com.bank.app.domain.runtime.repository.CustomerRepo;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.staging.entities.StgRelationship;
import com.bank.app.domain.staging.repository.StgRelationshipRepo;
import com.bank.app.util.ValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("relationshipTransformationProcessor")
public class RelationshipTransformationProcessor implements ItemProcessor<StgRelationship, Relationship> {

    private static final Logger LOGGER= LoggerFactory.getLogger(RelationshipTransformationProcessor.class);

    private final StgRelationshipRepo stgRelationshipRepo;

    private final AccountRepo accountRepo;
    private final CustomerRepo customerRepo;

    public RelationshipTransformationProcessor(StgRelationshipRepo stgRelationshipRepo
            , AccountRepo accountRepo
            , CustomerRepo customerRepo) {
        this.stgRelationshipRepo = stgRelationshipRepo;
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public Relationship process(@NonNull StgRelationship stgRelationship) throws RelationshipLoadError {
        LOGGER.info("Inside RelationshipTransformationProcessor.process() with Stg Relationship : {}",stgRelationship);

        Relationship relationship=new Relationship();
        relationship.setId(stgRelationship.getId());
        loadCustomer(stgRelationship, relationship);
        loadAccount(stgRelationship, relationship);
        relationship.setType(stgRelationship.getType());

        LOGGER.info("After Transformation relationship : {}",relationship);

        LOGGER.info("Change Stg relationship Validation Status to Sync");
        statusChangeToSync(stgRelationship);
        LOGGER.info("Stg Relationship Validation Status changed to Sync relationship : {}",stgRelationship);

        return relationship;
    }

    private void loadAccount(StgRelationship stgRelationship, Relationship relationship) throws RelationshipLoadError {
        Optional<Account> account=accountRepo.findById(stgRelationship.getAccountId().getAccountId());
        if(account.isPresent()){
            relationship.setAccountId(account.get());
        }else{
            throw new RelationshipLoadError("Unable to Load the relationship as account not found in runtime with Id : "+ stgRelationship.getAccountId().getAccountId());
        }
    }

    private void loadCustomer(StgRelationship stgRelationship, Relationship relationship) throws RelationshipLoadError {
        Optional<Customer> customer=customerRepo.findById(stgRelationship.getCustomerId().getCustomerId());

        if(customer.isPresent() ){
            relationship.setCustomerId(customer.get());
        }else{
            throw new RelationshipLoadError("Unable to Load the relationship as customer not found in runtime with Id : "+ stgRelationship.getCustomerId().getCustomerId());
        }
    }

    private void statusChangeToSync(StgRelationship stgRelationship){
        stgRelationship.setValidationStatus(ValidationStatus.SYNC);
        stgRelationshipRepo.save(stgRelationship);

    }
}
