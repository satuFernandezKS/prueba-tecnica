package com.satufernandezks.pruebatecnica.infrastructure.adapters;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.ports.spi.PricesPersistencePort;
import com.satufernandezks.pruebatecnica.infrastructure.entities.PricesEntity;
import com.satufernandezks.pruebatecnica.infrastructure.mappers.Mapper;
import com.satufernandezks.pruebatecnica.infrastructure.mappers.impl.PricesEntityToDomainMapper;
import com.satufernandezks.pruebatecnica.infrastructure.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PricesJpaAdapter implements PricesPersistencePort {

    @Autowired
    private PricesRepository pricesRepository;

    @Override
    public Optional<PricesDomain> getPrice(final PricesDomain pricesDomain) {
        final var pricesEntity = pricesRepository.getPrice(pricesDomain.getApplicationDate(),
                pricesDomain.getProductId(), pricesDomain.getBrandId());
        final Mapper<PricesDomain, PricesEntity> mapper = new PricesEntityToDomainMapper();
        return mapper.map(pricesEntity);
    }
}
