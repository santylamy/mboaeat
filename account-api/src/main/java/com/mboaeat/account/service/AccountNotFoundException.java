package com.mboaeat.account.service;

import com.mboaeat.common.exception.MboaEatException;

public class AccountNotFoundException extends MboaEatException {

    public AccountNotFoundException(String no_account_found_with_email) {
        super(no_account_found_with_email);
    }

    public AccountNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }
}
