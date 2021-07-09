package com.satufernandezks.pruebatecnica.application.adapters.impl;

import com.satufernandezks.pruebatecnica.application.adapters.PricesUseCase;
import com.satufernandezks.pruebatecnica.application.exceptions.MissingParameterException;
import com.satufernandezks.pruebatecnica.application.exceptions.NotFoundException;
import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.exceptions.ApplicationException;
import com.satufernandezks.pruebatecnica.domain.ports.api.PricesServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PricesUseCaseImpl implements PricesUseCase {

    private static final Logger logger = LoggerFactory.getLogger(PricesUseCaseImpl.class);

    private final PricesServicePort pricesServicePort;

    public PricesUseCaseImpl(PricesServicePort pricesServicePort) {
        this.pricesServicePort = pricesServicePort;
    }

    @Override
    public PricesDomain getPrice(PricesDomain pricesDomainRequest) throws NotFoundException, MissingParameterException, ApplicationException {

        logger.info("Entra en PricesUseCaseImpl.getPrice");
        logger.debug("Param applicationDate: {}", (pricesDomainRequest.getApplicationDate() != null
                ? pricesDomainRequest.getApplicationDate().toString() : ""));
        logger.debug("Param productId: {}", pricesDomainRequest.getProductId());
        logger.debug("Param brandId: {}", pricesDomainRequest.getBrandId());

        if (pricesDomainRequest.getApplicationDate() == null ||
                pricesDomainRequest.getProductId() == null ||
                pricesDomainRequest.getBrandId() == null) {
            logger.trace("Falta algún parámetro obligatorio");
            throw new MissingParameterException("Falta algún parámetro obligatorio");
        }

        Optional<PricesDomain> pricesDomainResult;
        try {
            logger.trace("Obteniendo valores del service");
            pricesDomainResult = pricesServicePort.getPrice(pricesDomainRequest);
        } catch (Exception e) {
            logger.trace("Se ha producido algún error interno");
            throw new ApplicationException(e);
        }
        if (pricesDomainResult.isEmpty()) {
            logger.trace("No se han encontrado resultados");
            throw new NotFoundException("No se han encontrado resultados");
        }

        return pricesDomainResult.get();
    }
}
