package com.achavez.bcnc.inditextest.product.domain;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(DomainErrorMessage domainErrorMessage) {
        super(domainErrorMessage.getMessage());
    }
}
