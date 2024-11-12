package com.achavez.bcnc.inditextest.product.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.achavez.bcnc.inditextest.product.domain.DomainErrorMessage.PRICE_ENDDATE_BEFORE_STARTDATE;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {
    @Test
    void createPriceWithValidDates() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);

        Price price = new Price(1L, startDate, endDate, 1L, 35455L, 0, new BigDecimal("35.50"), Currency.EUR);

        assertNotNull(price);
    }

    @Test
    void returnException_whenEndDateIsBeforeStartDate() {
        LocalDateTime startDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 0, 0);

        Executable executable = () -> new Price(1L, startDate, endDate, 1L, 35455L, 0, new BigDecimal("35.50"), Currency.EUR);

        DomainValidationException exception = assertThrows(DomainValidationException.class, executable);
        assertEquals(PRICE_ENDDATE_BEFORE_STARTDATE.getMessage(), exception.getMessage());
    }
    @Test
    void validateDateIsInRange() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59);
        Price price = new Price(1L, startDate, endDate, 1L, 35455L, 1, new BigDecimal("35.50"), Currency.EUR);

        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 15, 0);

        assertTrue(price.isDateInRange(testDate));
    }

    @Test
    void validateDateIsOutOfRange() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 23, 59);
        Price price = new Price(1L, startDate, endDate, 1L, 35455L, 1, new BigDecimal("35.50"), Currency.EUR);

        LocalDateTime testDate = LocalDateTime.of(2020, 6, 15, 15, 0);

        assertFalse(price.isDateInRange(testDate));
    }

}