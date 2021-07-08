package com.satufernandezks.pruebatecnica.domain.services;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.ports.api.PricesServicePort;
import com.satufernandezks.pruebatecnica.domain.ports.spi.PricesPersistencePort;

import java.util.Optional;

public class PricesServiceImpl implements PricesServicePort {

    private PricesPersistencePort pricesPersistencePort;

    public PricesServiceImpl(PricesPersistencePort pricesPersistencePort) {
        this.pricesPersistencePort = pricesPersistencePort;
    }

    @Override
    public Optional<PricesDomain> getPrice(final PricesDomain pricesDomain) {
        return pricesPersistencePort.getPrice(pricesDomain);
    }
}
