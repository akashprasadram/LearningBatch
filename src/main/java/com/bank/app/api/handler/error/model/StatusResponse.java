package com.bank.app.api.handler.error.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class StatusResponse {
    String errorMsg;
    HttpStatus status;
}
