package com.satufernandezks.pruebatecnica.domain.services;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.ports.api.PricesServicePort;
import com.satufernandezks.pruebatecnica.domain.ports.spi.PricesPersistencePort;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class PricesServiceTest {

    @TestConfiguration
    static class PricesServiceImplTestContextConfiguration {

        @MockBean
        PricesPersistencePort pricesPersistencePort;

        @Bean
        public PricesServicePort pricesServicePort() {
            return new PricesServiceImpl(pricesPersistencePort);
        }
    }

    @Autowired
    PricesServicePort pricesServicePort;

    @Autowired
    PricesPersistencePort pricesPersistencePort;

    @Test
    public void test_getPriceNotFound() {

        PricesDomain request = new PricesDomain();
        given(pricesPersistencePort.getPrice(mock(PricesDomain.class))).willReturn(Optional.empty());

        final Optional<PricesDomain> pricesDomain = pricesServicePort.getPrice(request);
        Assert.assertNotNull(pricesDomain);
        Assert.assertTrue(pricesDomain.isEmpty());
    }

    @Test
    public void test_getPrice() {

        PricesDomain request = new PricesDomain();
        request.setApplicationDate(LocalDateTime.now());
        request.setBrandId(1);
        request.setProductId(35455);
        given(pricesPersistencePort.getPrice(request)).willReturn(Optional.of(request));

        final Optional<PricesDomain> pricesDomain = pricesServicePort.getPrice(request);
        Assert.assertNotNull(pricesDomain);
        Assert.assertFalse(pricesDomain.isEmpty());
        Assert.assertEquals(request.getApplicationDate(), pricesDomain.get().getApplicationDate());
        Assert.assertEquals(request.getBrandId(), pricesDomain.get().getBrandId());
        Assert.assertEquals(request.getProductId(), pricesDomain.get().getProductId());
    }
}
