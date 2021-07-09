package com.satufernandezks.pruebatecnica.application.adapters;

import com.satufernandezks.pruebatecnica.application.adapters.impl.PricesUseCaseImpl;
import com.satufernandezks.pruebatecnica.application.exceptions.MissingParameterException;
import com.satufernandezks.pruebatecnica.application.exceptions.NotFoundException;
import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.exceptions.ApplicationException;
import com.satufernandezks.pruebatecnica.domain.ports.api.PricesServicePort;
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

@RunWith(SpringRunner.class)
public class PricesUseCaseTest {

    @TestConfiguration
    static class PricesServiceImplTestContextConfiguration {

        @MockBean
        PricesServicePort pricesServicePort;

        @Bean
        public PricesUseCase pricesUseCase() {
            return new PricesUseCaseImpl(pricesServicePort);
        }
    }

    @Autowired
    PricesServicePort pricesServicePort;

    @Autowired
    PricesUseCase pricesUseCase;

    @Test(expected = NotFoundException.class)
    public void test_getPriceNotFound() throws ApplicationException, NotFoundException, MissingParameterException {

        PricesDomain request = new PricesDomain();
        request.setApplicationDate(LocalDateTime.now());
        request.setProductId(0);
        request.setBrandId(0);
        given(pricesServicePort.getPrice(request)).willReturn(Optional.empty());

        pricesUseCase.getPrice(request);
    }

    @Test(expected = MissingParameterException.class)
    public void test_getPriceMissingParameter() throws ApplicationException, NotFoundException, MissingParameterException {

        PricesDomain request = new PricesDomain();
        pricesUseCase.getPrice(request);
    }

    @Test(expected = ApplicationException.class)
    public void test_getPriceError() throws ApplicationException, NotFoundException, MissingParameterException {

        PricesDomain request = new PricesDomain();
        request.setApplicationDate(LocalDateTime.now());
        request.setProductId(0);
        request.setBrandId(0);
        given(pricesServicePort.getPrice(request)).willThrow(RuntimeException.class);

        pricesUseCase.getPrice(request);
    }

    @Test
    public void test_getPrice() throws ApplicationException, NotFoundException, MissingParameterException {

        PricesDomain request = new PricesDomain();
        request.setApplicationDate(LocalDateTime.now());
        request.setBrandId(1);
        request.setProductId(35455);
        given(pricesServicePort.getPrice(request)).willReturn(Optional.of(request));

        final PricesDomain pricesDomain = pricesUseCase.getPrice(request);
        Assert.assertNotNull(pricesDomain);
        Assert.assertEquals(request.getApplicationDate(), pricesDomain.getApplicationDate());
        Assert.assertEquals(request.getBrandId(), pricesDomain.getBrandId());
        Assert.assertEquals(request.getProductId(), pricesDomain.getProductId());
    }
}
