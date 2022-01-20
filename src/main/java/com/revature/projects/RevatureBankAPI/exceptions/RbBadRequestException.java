package com.revature.projects.RevatureBankAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RbBadRequestException extends RuntimeException {

    public RbBadRequestException(String message) {

        super(message);

    }

}