package com.satufernandezks.pruebatecnica.infrastructure.vo.impl;

import com.satufernandezks.pruebatecnica.infrastructure.vo.ViewObject;
import org.springframework.http.HttpStatus;

public class ErrorVO extends ViewObject {

    protected String message;

    public ErrorVO(String message) {
        super(HttpStatus.NOT_FOUND.value());
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
