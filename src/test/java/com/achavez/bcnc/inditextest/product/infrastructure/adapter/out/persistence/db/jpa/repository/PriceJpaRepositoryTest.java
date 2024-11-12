package com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.repository;

import com.achavez.bcnc.inditextest.product.TestConfig;
import com.achavez.bcnc.inditextest.product.domain.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestConfig
class PriceJpaRepositoryTest {

    @Autowired
    private PriceJpaRepository priceJpaRepository;

    @Test
    void findByBrandIdAndProductIdAndDate() {
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);

        List<Price> prices = priceJpaRepository.findByBrandIdAndProductIdAndDate(
                brandId, productId, applicationDate);

        assertFalse(prices.isEmpty());
        Price price = prices.get(0);
        assertEquals(brandId, price.brandId());
        assertEquals(productId, price.productId());
        assertEquals(1L, price.priceList());
    }
}