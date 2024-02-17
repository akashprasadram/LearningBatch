package com.bank.app.domain.common.error;

import com.bank.app.domain.common.error.exceptions.DataCreationError;
import com.bank.app.domain.common.error.exceptions.DataNotFoundException;
import com.bank.app.domain.common.error.model.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(DataCreationError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StatusResponse> dataCreationErrorHandler(Exception e) {
        // Handle the exception globally
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        statusResponse.setErrorMsg(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statusResponse);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<StatusResponse> handleException(Exception e) {
        // Handle the exception globally
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setStatus(HttpStatus.NOT_FOUND);
        statusResponse.setErrorMsg(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(statusResponse);
    }
}
