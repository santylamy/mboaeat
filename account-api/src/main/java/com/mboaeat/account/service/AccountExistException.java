package com.mboaeat.account.service;

import com.mboaeat.common.exception.MboaEatException;

public class AccountExistException extends MboaEatException {
    public AccountExistException(String s) {
        super(s);
    }

    public AccountExistException(String message, String errorCode) {
        super(message, errorCode);
    }
}
