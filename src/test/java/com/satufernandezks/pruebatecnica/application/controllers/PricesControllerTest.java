package com.satufernandezks.pruebatecnica.application.controllers;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.ports.api.PricesServicePort;
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
import java.util.Optional;

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
    private PricesServicePort pricesServicePort;

    @Test
    public void test_fail_applicationDate_param() throws Exception {

        mvc.perform(get("/prices/?productId=35455&brandId=1"))
                .andExpect(status().is4xxClientError());
    }

    @Test(expected = Exception.class)
    public void test_fail_error() throws Exception {

        given(pricesServicePort.getPrice(any(PricesDomain.class))).willThrow(Exception.class);
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

        Optional<PricesDomain> pricesDomainResult = Optional.of(domainRequest);

        given(pricesServicePort.getPrice(any(PricesDomain.class))).willReturn(pricesDomainResult);

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

        given(pricesServicePort.getPrice(any(PricesDomain.class))).willReturn(Optional.empty());

        mvc.perform(get("/prices/?applicationDate=" + domainRequest.getApplicationDate().toString()
                + "&productId=" + domainRequest.getProductId()
                + "&brandId=" + domainRequest.getBrandId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
