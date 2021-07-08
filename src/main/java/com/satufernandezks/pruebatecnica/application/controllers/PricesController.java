package com.satufernandezks.pruebatecnica.application.controllers;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.ports.api.PricesServicePort;
import com.satufernandezks.pruebatecnica.infrastructure.mappers.Mapper;
import com.satufernandezks.pruebatecnica.infrastructure.mappers.impl.PricesDomainToResponseMapper;
import com.satufernandezks.pruebatecnica.infrastructure.responses.PricesResponse;
import com.satufernandezks.pruebatecnica.infrastructure.vo.ViewObject;
import com.satufernandezks.pruebatecnica.infrastructure.vo.impl.ErrorVO;
import com.satufernandezks.pruebatecnica.infrastructure.vo.impl.PricesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/prices")
public class PricesController {

    private static Logger logger = LoggerFactory.getLogger(PricesController.class);

    private PricesServicePort pricesServicePort;

    @Autowired
    public PricesController(final PricesServicePort pricesServicePort) {
        this.pricesServicePort = pricesServicePort;
    }

    @GetMapping("/")
    public ResponseEntity<ViewObject> getPrice(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(name = "applicationDate", required = false) final LocalDateTime applicationDate,
            @RequestParam(name = "productId", required = false) final Integer productId,
            @RequestParam(name = "brandId", required = false) final Integer brandId) {

        logger.info("Entra en PricesController.getPrice");
        logger.debug("Param applicationDate: " + (applicationDate != null ? applicationDate.toString() : ""));
        logger.debug("Param productId: " + productId);
        logger.debug("Param brandId: " + brandId);

        ViewObject response;
        if (applicationDate == null || productId == null || brandId == null) {
            response = new ErrorVO("Falta algún parámetro obligatorio");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        final PricesDomain pricesDomainRequest = new PricesDomain();
        pricesDomainRequest.setApplicationDate(applicationDate);
        pricesDomainRequest.setProductId(productId);
        pricesDomainRequest.setBrandId(brandId);

        Optional<PricesDomain> pricesDomainResult;
        try {
            logger.trace("Obteniendo valores del service");
            pricesDomainResult = pricesServicePort.getPrice(pricesDomainRequest);
            if (pricesDomainResult.isPresent()) {
                logger.trace("Valores recuperados");
                final Mapper pricesDomainToResponseMapper = new PricesDomainToResponseMapper();
                Optional<PricesResponse> pricesResponse = pricesDomainToResponseMapper.map(pricesDomainResult.get());
                response = new PricesVO(pricesResponse.get());
            } else {
                logger.trace("No ha encontrado");
                response = new ErrorVO("No se han encontrado resultados");
                return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Se ha producido un error", e);
            response = new ErrorVO("Se ha producido un error");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
