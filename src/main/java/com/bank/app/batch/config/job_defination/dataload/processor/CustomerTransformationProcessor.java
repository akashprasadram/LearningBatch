package com.bank.app.batch.config.job_defination.dataload.processor;

import com.bank.app.domain.runtime.entities.Customer;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.staging.repository.StgCustomerRepo;
import com.bank.app.util.ValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component("customerTransformationProcessor")
public class CustomerTransformationProcessor implements ItemProcessor<StgCustomer, Customer> {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerTransformationProcessor.class);

    private final StgCustomerRepo stgCustomerRepo;

    public CustomerTransformationProcessor(StgCustomerRepo stgCustomerRepo) {
        this.stgCustomerRepo = stgCustomerRepo;
    }

    @Override
    public Customer process(@NonNull StgCustomer stgCustomer) throws Exception {
        LOGGER.info("Inside CustomerTransformationProcessor.process() with Stg Customer : {}",stgCustomer);
        Customer customer=new Customer();
        customer.setCustomerId(stgCustomer.getCustomerId());
        customer.setCustomerName(stgCustomer.getCustomerName());
        customer.setDob(stgCustomer.getDob());
        customer.setAddress(stgCustomer.getAddress());

        LOGGER.info("After Transformation Customer : {}",customer);
        LOGGER.info("Change Stg Customer Validation Status to Sync");
        statusChangeToSync(stgCustomer);
        LOGGER.info("Stg Customer Validation Status changed to Sync Customer : {}",stgCustomer);
        return customer;
    }

    private void statusChangeToSync(StgCustomer stgCustomer){
        stgCustomer.setValidationStatus(ValidationStatus.SYNC);
        stgCustomerRepo.save(stgCustomer);

    }
}
