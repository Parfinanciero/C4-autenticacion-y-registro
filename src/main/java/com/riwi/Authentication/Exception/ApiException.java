package com.riwi.Authentication.Exception;

import com.riwi.Authentication.Exception.model.ErrorDetails;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ApiException extends RuntimeException {

    private HttpStatus status;
    private List<ErrorDetails> errorDetailList = new ArrayList<>();

    // Constructor corregido
    public ApiException(HttpStatus status, String message, List<ErrorDetails> errorDetailList) {
        super(message);
        this.status = status;
        this.errorDetailList = errorDetailList;
    }

    // Getters y setters
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<ErrorDetails> getErrorDetailList() {
        return errorDetailList;
    }

    public void setErrorDetailList(List<ErrorDetails> errorDetailList) {
        this.errorDetailList = errorDetailList;
    }
}
