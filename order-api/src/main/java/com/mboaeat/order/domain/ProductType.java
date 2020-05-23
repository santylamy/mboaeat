package com.mboaeat.order.domain;

public enum ProductType {
    SAUCE("Sauce");

    private String value;

    ProductType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
