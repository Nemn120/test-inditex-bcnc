package com.achavez.bcnc.inditextest.product.domain;

public enum Currency {

    EUR("€");

    private final String value;
    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
