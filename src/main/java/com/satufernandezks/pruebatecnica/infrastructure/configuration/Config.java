package com.satufernandezks.pruebatecnica.infrastructure.configuration;

import com.satufernandezks.pruebatecnica.domain.ports.api.PricesServicePort;
import com.satufernandezks.pruebatecnica.domain.ports.spi.PricesPersistencePort;
import com.satufernandezks.pruebatecnica.domain.services.PricesServiceImpl;
import com.satufernandezks.pruebatecnica.infrastructure.adapters.PricesJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public PricesPersistencePort pricesPersistence() {
        return new PricesJpaAdapter();
    }

    @Bean
    public PricesServicePort pricesService() {
        return new PricesServiceImpl(pricesPersistence());
    }
}