package com.satufernandezks.pruebatecnica.infrastructure.repository;

import com.satufernandezks.pruebatecnica.domain.data.PricesDomain;
import com.satufernandezks.pruebatecnica.infrastructure.entities.PricesEntity;
import com.satufernandezks.pruebatecnica.infrastructure.repository.impl.PricesRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PricesRepositoryTest {

    @TestConfiguration
    static class PricesRepositoryImplTestContextConfiguration {

        @Bean
        public PricesRepository pricesRepository() {
            return new PricesRepositoryImpl();
        }
    }

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PricesRepository pricesRepository;

    @Test
    public void test_getPrice() {

        PricesEntity pricesEntity = new PricesEntity(null, 1, LocalDateTime.now(), LocalDateTime.now(),
                1, 35455, 1, 35.50f, "EUR", LocalDateTime.now(), "admin");
        testEntityManager.persist(pricesEntity);

        PricesEntity pricesEntityResult = pricesRepository.getPrice(pricesEntity.getStartDate(),
                pricesEntity.getProductId(), pricesEntity.getBrandId());
        Assert.assertNotNull(pricesEntityResult);
        Assert.assertEquals(pricesEntityResult.getBrandId(), pricesEntity.getBrandId());
        Assert.assertEquals(pricesEntityResult.getStartDate(), pricesEntity.getStartDate());
        Assert.assertEquals(pricesEntityResult.getEndDate(), pricesEntity.getEndDate());
        Assert.assertEquals(pricesEntityResult.getProductId(), pricesEntity.getProductId());
        Assert.assertEquals(pricesEntityResult.getPrice(), pricesEntity.getPrice());
        Assert.assertEquals(pricesEntityResult.getPriceList(), pricesEntity.getPriceList());
    }

    @Test
    public void test_getPriceNotFound() {

        PricesDomain pricesDomain = mock(PricesDomain.class);
        when(pricesDomain.getApplicationDate()).thenReturn(LocalDateTime.now());
        when(pricesDomain.getProductId()).thenReturn(35455);
        when(pricesDomain.getBrandId()).thenReturn(1);
        PricesEntity pricesEntityResult = pricesRepository.getPrice(pricesDomain.getApplicationDate(),
                pricesDomain.getProductId(), pricesDomain.getBrandId());
        Assert.assertNull(pricesEntityResult);
    }
}
