package com.bank.app.batch.config.job_defination.data_validation.listener;

import com.bank.app.domain.staging.entities.StgCustomer;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component("customerValidationSkipListener")
public class CustomerValidationSkipListener implements SkipListener<StgCustomer, StgCustomer> {

}
