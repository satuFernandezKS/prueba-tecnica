package com.satufernandezks.pruebatecnica.infrastructure.repository;

import com.satufernandezks.pruebatecnica.infrastructure.entities.PricesEntity;

import java.time.LocalDateTime;

public interface PricesRepository /*extends JpaRepository<PricesEntity, Long>*/ {

    PricesEntity getPrice(final LocalDateTime applicationDate,
                          final Integer productId,
                          final Integer brandId);
}
