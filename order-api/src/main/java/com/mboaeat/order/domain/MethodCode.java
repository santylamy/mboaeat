package com.mboaeat.order.domain;

import java.util.List;

public enum MethodCode {
    CREDIT_CARD,
    MOBILE_PAY,
    MANUEL;

    private static final List<MethodCode> AUTOMATIC_PAYMENT_CODES = List.of(CREDIT_CARD, MOBILE_PAY);


    public boolean isNumeric(){
        return AUTOMATIC_PAYMENT_CODES.contains(this);
    }

    public boolean isManual(){
        return MANUEL.equals(this);
    }
}
