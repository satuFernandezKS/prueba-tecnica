package com.satufernandezks.pruebatecnica.infrastructure.vo.impl;

import com.satufernandezks.pruebatecnica.infrastructure.responses.PricesResponse;
import com.satufernandezks.pruebatecnica.infrastructure.vo.ViewObject;
import org.springframework.http.HttpStatus;

public class PricesVO extends ViewObject {

    protected PricesResponse data;

    public PricesVO(PricesResponse data) {
        super(HttpStatus.OK.value());
        this.data = data;
    }

    public PricesResponse getData() {
        return data;
    }
}
