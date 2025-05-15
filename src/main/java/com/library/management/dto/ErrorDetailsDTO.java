package com.library.management.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Generic DTO To handle and throw all exceptions from applications*
 */
public class ErrorDetailsDTO {
    private Date timestamp;
    private List<String> messages = new ArrayList<>();
    private String details;
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorDetailsDTO(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.messages.add(message);
        this.details = details;
    }

    public ErrorDetailsDTO(Date timestamp, List<String> message, String details) {
        super();
        this.timestamp = timestamp;
        this.messages = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public List<String> getMessage() {
        return messages;
    }

    public String getDetails() {
        return details;
    }
}
