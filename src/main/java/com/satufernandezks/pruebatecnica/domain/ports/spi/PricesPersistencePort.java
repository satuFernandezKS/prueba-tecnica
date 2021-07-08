package com.satufernandezks.pruebatecnica.domain.ports.spi;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;

import java.util.Optional;

public interface PricesPersistencePort {

    Optional<PricesDomain> getPrice(PricesDomain pricesDomain);
}
