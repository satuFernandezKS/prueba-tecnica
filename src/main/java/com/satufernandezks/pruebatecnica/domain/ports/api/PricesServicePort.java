package com.satufernandezks.pruebatecnica.domain.ports.api;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;

import java.util.Optional;

public interface PricesServicePort {

    Optional<PricesDomain> getPrice(PricesDomain pricesDomain);
}
