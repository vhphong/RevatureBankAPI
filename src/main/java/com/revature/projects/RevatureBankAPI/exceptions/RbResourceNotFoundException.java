package com.revature.projects.RevatureBankAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RbResourceNotFoundException extends RuntimeException {

    public RbResourceNotFoundException(String message) {

        super(message);

    }

}
