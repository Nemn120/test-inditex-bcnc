package com.achavez.bcnc.inditextest.product.domain;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException(DomainErrorMessage domainErrorMessage) {
        super(domainErrorMessage.getMessage());
    }
}
