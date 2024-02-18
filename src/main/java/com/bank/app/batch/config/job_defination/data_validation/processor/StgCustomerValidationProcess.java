package com.bank.app.batch.config.job_defination.data_validation.processor;

import com.bank.app.domain.common.error.exceptions.StgCustomerValidationError;
import com.bank.app.domain.staging.entities.StgCustomer;
import com.bank.app.domain.staging.entities.StgRelationship;
import com.bank.app.domain.staging.repository.StgRelationshipRepo;
import com.bank.app.util.ValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component("stgCustomerValidationProcess")
public class StgCustomerValidationProcess implements ItemProcessor<StgCustomer, StgCustomer> {

    private static final Logger LOGGER= LoggerFactory.getLogger(StgCustomerValidationProcess.class);
    private final StgRelationshipRepo stgRelationshipRepo;

    public StgCustomerValidationProcess(StgRelationshipRepo stgRelationshipRepo) {
        this.stgRelationshipRepo = stgRelationshipRepo;
    }

    @Override
    public StgCustomer process(@NonNull StgCustomer stgCustomer) throws Exception {
        LOGGER.info("Inside StgCustomerValidationProcess.process() with Stg Customer : {}",stgCustomer);
        validateCustomer(stgCustomer);
        return stgCustomer;
    }

    public void validateCustomer(StgCustomer stgCustomer) throws StgCustomerValidationError {
        LOGGER.info("Inside validateCustomer() with Stg Customer : {}",stgCustomer);

        List<String> comments=new ArrayList<>();

        relationshipCheckValidator(stgCustomer, comments);
        ageValidator(stgCustomer, comments);

        if(!comments.isEmpty()) {
            LOGGER.info("Validation Error with Comment : {}" , comments);
            stgCustomer.setComment(comments.toString());
            stgCustomer.setValidationStatus(ValidationStatus.FAIL);
            throw new StgCustomerValidationError("Customer validation failed with Errors : "+comments.toString());
        }
        else {
            stgCustomer.setValidationStatus(ValidationStatus.PASS);
        }
    }

    private void relationshipCheckValidator(StgCustomer stgCustomer, List<String> comments) {
        List<StgRelationship> resultStgAccount  = stgRelationshipRepo.findByCustomerId(stgCustomer);
        if (resultStgAccount.isEmpty()) {
            comments.add("Customer is not associated with any account");
        }
    }

    private void ageValidator(StgCustomer stgCustomer, List<String> comments) {
        // Your DOB string in the specified format
        String dobString = String.valueOf(stgCustomer.getDob());

        // Define the date-time formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Parse the DOB string into a LocalDateTime object
        LocalDateTime dobDateTime = LocalDateTime.parse(dobString, formatter);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the age
        int age = calculateAge(dobDateTime.toLocalDate(), currentDate);

        // Check if the age is 18 or more
        if (age < 18) {
            comments.add("Customer Age is less than 18 years");
        }
    }

    private int calculateAge(LocalDate dob, LocalDate currentDate) {
        return currentDate.getYear() - dob.getYear();
    }
}