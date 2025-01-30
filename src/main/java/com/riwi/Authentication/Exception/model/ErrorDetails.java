package com.riwi.Authentication.Exception.model;

import jakarta.persistence.Convert;

public class ErrorDetails {
    private String attributeName;
    private String reason;


    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ErrorDetails(String attributeName, String reason) {
        this.attributeName = attributeName;
        this.reason = reason;
    }

    public ErrorDetails() {
    }
}
