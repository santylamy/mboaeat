package com.mboaeat.common.exception;

import com.mboaeat.domain.Constants;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class ResourceNotFoundException extends MboaEatException {

    public final int errorCode;

    public ResourceNotFoundException(){
        this.errorCode = HTTP_NOT_FOUND;
    }

    public ResourceNotFoundException(final String message, final Throwable cause){
        super(message, cause, Constants.Message.RESOURCE_NOT_FOUND);
        this.errorCode = HTTP_NOT_FOUND;
    }

}
