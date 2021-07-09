package com.satufernandezks.pruebatecnica.application.adapters;

import com.satufernandezks.pruebatecnica.application.exceptions.MissingParameterException;
import com.satufernandezks.pruebatecnica.application.exceptions.NotFoundException;
import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.exceptions.ApplicationException;

public interface PricesUseCase {

    PricesDomain getPrice(PricesDomain pricesDomain) throws NotFoundException, MissingParameterException, ApplicationException;
}
