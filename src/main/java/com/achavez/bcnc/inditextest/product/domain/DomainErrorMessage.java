package com.achavez.bcnc.inditextest.product.domain;

import lombok.Getter;

@Getter
public enum DomainErrorMessage {

    PRICE_NOT_COMBINE("No valid price was found for the given combination of brand, product and date."),
    PRICE_ENDDATE_BEFORE_STARTDATE("The price end date cannot be earlier than the start date.");

    private final String message;

    DomainErrorMessage(String message) {
        this.message= message;
    }

}
