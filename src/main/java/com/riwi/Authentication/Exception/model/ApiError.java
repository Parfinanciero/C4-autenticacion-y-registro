package com.riwi.Authentication.Exception.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private List<ErrorDetails> errorDetailList;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorDetails> getErrorDetailList() {
        return errorDetailList;
    }

    public void setErrorDetailList(List<ErrorDetails> errorDetailList) {
        this.errorDetailList = errorDetailList;
    }

    public ApiError(HttpStatus httpStatus, String message, List<ErrorDetails> errorDetailList) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.errorDetailList = errorDetailList;
    }

    public ApiError() {
    }
}
