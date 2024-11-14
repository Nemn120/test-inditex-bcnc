package com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.repository;

import com.achavez.bcnc.inditextest.product.TestConfig;
import com.achavez.bcnc.inditextest.product.domain.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

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

        Optional<Price> optionalPrice = priceJpaRepository.findTopWithHighestPriority(
                brandId, productId, applicationDate);

        assertFalse(optionalPrice.isEmpty());
        assertEquals(brandId, optionalPrice.get().brandId());
        assertEquals(productId, optionalPrice.get().productId());
        assertEquals(1L, optionalPrice.get().priceList());
    }
}