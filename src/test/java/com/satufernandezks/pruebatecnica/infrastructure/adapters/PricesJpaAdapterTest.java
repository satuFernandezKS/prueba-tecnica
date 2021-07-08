package com.satufernandezks.pruebatecnica.infrastructure.adapters;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.domain.ports.spi.PricesPersistencePort;
import com.satufernandezks.pruebatecnica.infrastructure.entities.PricesEntity;
import com.satufernandezks.pruebatecnica.infrastructure.repository.PricesRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class PricesJpaAdapterTest {

    @TestConfiguration
    static class PricesJpaAdapterTestContextConfiguration {

        @Bean
        public PricesPersistencePort pricesPersistencePort() {
            return new PricesJpaAdapter();
        }
    }

    @MockBean
    PricesRepository pricesRepository;

    @Autowired
    PricesPersistencePort pricesPersistencePort;

    @Test
    public void test_getPrice() {

        PricesDomain pricesDomain = mock(PricesDomain.class);
        PricesEntity pricesEntity = mock(PricesEntity.class);
        given(pricesRepository.getPrice(pricesDomain.getApplicationDate(), pricesDomain.getProductId(),
                pricesDomain.getBrandId())).willReturn(pricesEntity);

        final Optional<PricesDomain> pricesDomainResult = pricesPersistencePort.getPrice(pricesDomain);
        Assert.assertNotNull(pricesDomainResult);
        Assert.assertFalse(pricesDomainResult.isEmpty());
        Assert.assertEquals(pricesDomainResult.get().getProductId(), pricesEntity.getProductId());
        Assert.assertEquals(pricesDomainResult.get().getBrandId(), pricesEntity.getBrandId());
        Assert.assertEquals(pricesDomainResult.get().getStartDate(), pricesEntity.getStartDate());
        Assert.assertEquals(pricesDomainResult.get().getEndDate(), pricesEntity.getEndDate());
    }

    @Test
    public void test_getPriceNotFound() {

        PricesDomain pricesDomain = mock(PricesDomain.class);
        given(pricesRepository.getPrice(pricesDomain.getApplicationDate(), pricesDomain.getProductId(),
                pricesDomain.getBrandId())).willReturn(null);

        final Optional<PricesDomain> pricesDomainResult = pricesPersistencePort.getPrice(pricesDomain);
        Assert.assertNotNull(pricesDomainResult);
        Assert.assertTrue(pricesDomainResult.isEmpty());
    }
}
