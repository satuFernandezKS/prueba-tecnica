package com.satufernandezks.pruebatecnica.infrastructure.mappers.impl;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.infrastructure.mappers.Mapper;
import com.satufernandezks.pruebatecnica.infrastructure.responses.PricesResponse;

import java.util.Optional;

public class PricesDomainToResponseMapper implements Mapper<PricesResponse, PricesDomain> {

    @Override
    public Optional<PricesResponse> map(final PricesDomain domain) {

        if (domain == null) {
            return Optional.empty();
        }
        final var pricesResponse = new PricesResponse();
        pricesResponse.setBrandId(domain.getBrandId());
        pricesResponse.setStartDate(domain.getStartDate());
        pricesResponse.setEndDate(domain.getEndDate());
        pricesResponse.setPriceList(domain.getPriceList());
        pricesResponse.setProductId(domain.getProductId());
        pricesResponse.setPrice(domain.getPrice());
        return Optional.of(pricesResponse);
    }
}
