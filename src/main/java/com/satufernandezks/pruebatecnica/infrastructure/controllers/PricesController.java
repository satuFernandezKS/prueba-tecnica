package com.satufernandezks.pruebatecnica.infrastructure.controllers;

import com.satufernandezks.pruebatecnica.application.adapters.PricesUseCase;
import com.satufernandezks.pruebatecnica.application.exceptions.MissingParameterException;
import com.satufernandezks.pruebatecnica.application.exceptions.NotFoundException;
import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.exceptions.ApplicationException;
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

    private static final Logger logger = LoggerFactory.getLogger(PricesController.class);

    private final PricesUseCase pricesUseCase;

    @Autowired
    public PricesController(final PricesUseCase pricesUseCase) {
        this.pricesUseCase = pricesUseCase;
    }

    @GetMapping("/")
    public ResponseEntity<ViewObject> getPrice(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(name = "applicationDate", required = false) final LocalDateTime applicationDate,
            @RequestParam(name = "productId", required = false) final Integer productId,
            @RequestParam(name = "brandId", required = false) final Integer brandId) {

        logger.info("Entra en PricesController.getPrice");

        final var pricesDomainRequest = new PricesDomain();
        pricesDomainRequest.setApplicationDate(applicationDate);
        pricesDomainRequest.setProductId(productId);
        pricesDomainRequest.setBrandId(brandId);

        ViewObject response;
        try {
            logger.trace("Obteniendo valores del caso de uso");
            final var pricesDomainResult = pricesUseCase.getPrice(pricesDomainRequest);
            logger.trace("Valores recuperados");
            final Mapper<PricesResponse, PricesDomain> pricesDomainToResponseMapper = new PricesDomainToResponseMapper();
            Optional<PricesResponse> pricesResponse = pricesDomainToResponseMapper.map(pricesDomainResult);
            response = new PricesVO(pricesResponse.orElseGet(PricesResponse::new));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (MissingParameterException e) {
            logger.error(e.getMessage(), e);
            response = new ErrorVO(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            logger.error(e.getMessage(), e);
            response = new ErrorVO(e.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
            response = new ErrorVO(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Se ha producido un error", e);
            response = new ErrorVO("Se ha producido un error");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
