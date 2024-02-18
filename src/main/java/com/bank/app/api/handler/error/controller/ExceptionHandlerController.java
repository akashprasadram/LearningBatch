package com.bank.app.api.handler.error.controller;

import com.bank.app.domain.common.error.exceptions.DataIngestionError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.common.error.exceptions.InvalidJobNameException;
import com.bank.app.domain.common.error.exceptions.JobStartException;
import com.bank.app.api.handler.error.model.StatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER= LoggerFactory.getLogger(ExceptionHandlerController.class);
    @ExceptionHandler(DataIngestionError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StatusResponse> dataCreationErrorExceptionHandler(Exception e) {

        LOGGER.error("Inside dataCreationErrorExceptionHandler() with Error : {}",e.getMessage());
        // Handle the exception globally
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        statusResponse.setErrorMsg(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statusResponse);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<StatusResponse> dataNotFoundExceptionHandler(Exception e) {

        LOGGER.error("Inside dataNotFoundExceptionHandler() with Error : {}",e.getMessage());
        // Handle the exception globally
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setStatus(HttpStatus.NOT_FOUND);
        statusResponse.setErrorMsg(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(statusResponse);
    }

    @ExceptionHandler(InvalidJobNameException.class)
    public ResponseEntity<StatusResponse> invalidJobNameException(Exception e) {

        LOGGER.error("Inside InvalidJobNameException() with Error : {}",e.getMessage());
        // Handle the exception globally
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setStatus(HttpStatus.NOT_FOUND);
        statusResponse.setErrorMsg(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(statusResponse);
    }

    @ExceptionHandler(JobStartException.class)
    public ResponseEntity<StatusResponse> jobStartException(Exception e) {

        LOGGER.error("Inside InvalidJobNameException() with Error : {}",e.getMessage());
        // Handle the exception globally
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setStatus(HttpStatus.NOT_FOUND);
        statusResponse.setErrorMsg(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(statusResponse);
    }
}
