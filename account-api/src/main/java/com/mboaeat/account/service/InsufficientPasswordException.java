package com.mboaeat.account.service;

import com.mboaeat.common.exception.MboaEatException;

/**
 * Throw if password account modification is re not
 * sufficiently trusted.
 */
public class InsufficientPasswordException extends MboaEatException {

    public InsufficientPasswordException(String message) {
        super(message);
    }
}
