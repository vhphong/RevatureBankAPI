package com.revature.projects.RevatureBankAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RevBankBadRequestException extends RuntimeException {

	public RevBankBadRequestException(String message) {

		super(message);

	}

}
