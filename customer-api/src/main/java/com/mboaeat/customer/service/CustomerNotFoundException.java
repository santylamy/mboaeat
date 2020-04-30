package com.mboaeat.customer.service;

import com.mboaeat.common.exception.MboaEatException;

public class CustomerNotFoundException extends MboaEatException {
    public CustomerNotFoundException(String msg, String code) {
        super(msg, code);
    }
}
