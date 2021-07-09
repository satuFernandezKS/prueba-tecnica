package com.satufernandezks.pruebatecnica.infrastructure.mappers.impl;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.infrastructure.entities.PricesEntity;
import com.satufernandezks.pruebatecnica.infrastructure.mappers.Mapper;

import java.util.Optional;

public class PricesEntityToDomainMapper implements Mapper<PricesDomain, PricesEntity> {

    @Override
    public Optional<PricesDomain> map(final PricesEntity entity) {

        if (entity == null) {
            return Optional.empty();
        }
        final var pricesDomain = new PricesDomain();
        pricesDomain.setBrandId(entity.getBrandId());
        pricesDomain.setStartDate(entity.getStartDate());
        pricesDomain.setEndDate(entity.getEndDate());
        pricesDomain.setPriceList(entity.getPriceList());
        pricesDomain.setProductId(entity.getProductId());
        pricesDomain.setPriority(entity.getPriority());
        pricesDomain.setPrice(entity.getPrice());
        pricesDomain.setCurrency(entity.getCurrency());
        pricesDomain.setLastUpdate(entity.getLastUpdate());
        pricesDomain.setLastUpdateBy(entity.getLastUpdateBy());
        return Optional.of(pricesDomain);
    }
}
