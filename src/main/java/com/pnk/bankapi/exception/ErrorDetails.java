package com.pnk.bankapi.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}