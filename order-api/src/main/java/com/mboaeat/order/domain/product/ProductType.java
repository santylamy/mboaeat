package com.mboaeat.order.domain.product;

public enum ProductType {
    SAUCE("Sauce"),
    VEGETABLE("Vegetable");

    private String value;

    ProductType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
