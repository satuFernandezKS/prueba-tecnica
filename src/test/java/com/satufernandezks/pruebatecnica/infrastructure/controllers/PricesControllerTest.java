package com.satufernandezks.pruebatecnica.infrastructure.controllers;

import com.satufernandezks.pruebatecnica.application.adapters.PricesUseCase;
import com.satufernandezks.pruebatecnica.application.exceptions.MissingParameterException;
import com.satufernandezks.pruebatecnica.application.exceptions.NotFoundException;
import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.exceptions.ApplicationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PricesController.class)
public class PricesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PricesUseCase pricesUseCase;

    @Test
    public void test_fail_applicationDate_param() throws Exception {

        given(pricesUseCase.getPrice(any(PricesDomain.class))).willThrow(MissingParameterException.class);
        mvc.perform(get("/prices/?productId=35455&brandId=1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void test_fail_error() throws Exception {

        given(pricesUseCase.getPrice(any(PricesDomain.class))).willThrow(ApplicationException.class);
        mvc.perform(get("/prices/?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void test_getPrice() throws Exception {

        final PricesDomain domainRequest = new PricesDomain();
        domainRequest.setApplicationDate(LocalDateTime.of(2020, Month.JUNE, 14, 10, 00, 00));
        domainRequest.setProductId(35455);
        domainRequest.setBrandId(1);
        domainRequest.setStartDate(domainRequest.getApplicationDate());
        domainRequest.setEndDate(domainRequest.getApplicationDate());
        domainRequest.setPrice(35.50f);
        domainRequest.setPriceList(1);
        domainRequest.setPriority(1);
        domainRequest.setCurrency("EUR");
        domainRequest.setLastUpdate(LocalDateTime.now());
        domainRequest.setLastUpdateBy("admin");

        given(pricesUseCase.getPrice(any(PricesDomain.class))).willReturn(domainRequest);

        mvc.perform(get("/prices/?applicationDate=" + domainRequest.getApplicationDate().toString()
                + "&productId=" + domainRequest.getProductId()
                + "&brandId=" + domainRequest.getBrandId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_getPriceNotFound() throws Exception {

        final PricesDomain domainRequest = new PricesDomain();
        domainRequest.setApplicationDate(LocalDateTime.of(2020, Month.JUNE, 14, 10, 00, 00));
        domainRequest.setProductId(35455);
        domainRequest.setBrandId(1);

        given(pricesUseCase.getPrice(any(PricesDomain.class))).willThrow(NotFoundException.class);

        mvc.perform(get("/prices/?applicationDate=" + domainRequest.getApplicationDate().toString()
                + "&productId=" + domainRequest.getProductId()
                + "&brandId=" + domainRequest.getBrandId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
