package com.satufernandezks.pruebatecnica.application.exceptions;

public class MissingParameterException extends Exception {

    public MissingParameterException() {
        super();
    }

    public MissingParameterException(String message) {
        super(message);
    }
}
