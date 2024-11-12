package com.achavez.bcnc.inditextest.product.application.service;

import com.achavez.bcnc.inditextest.product.application.port.out.PriceRepository;
import com.achavez.bcnc.inditextest.product.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.achavez.bcnc.inditextest.product.domain.DomainErrorMessage.PRICE_DUPLICATE_MAX_PRIORITY;
import static com.achavez.bcnc.inditextest.product.domain.DomainErrorMessage.PRICE_NOT_VALID_RANGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPriceProductByDateServiceTest {

    private static final Logger logger = LogManager.getLogger(GetPriceProductByDateServiceTest.class);

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private GetPriceProductByDateService priceService;

    @Test
    void execute_returnPriceValid() {
        logger.info("execute_returnPriceValid()");
        Long brandId = 1L;
        Long productId = 1L;
        Integer priority = 1;
        LocalDateTime dateTime = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);
        Price price = new Price(brandId, dateTime, dateTime, 1L, productId, priority, new BigDecimal("33.2"), Currency.EUR);

        when(priceRepository.findByBrandIdAndProductIdAndDate(brandId, brandId, dateTime))
                .thenReturn(List.of(price));

        Price result = priceService.execute(brandId, brandId, dateTime);
        assertNotNull(result);
        assertEquals(brandId, result.brandId());
        assertEquals(productId, result.productId());
        assertEquals(priority, result.priority());
    }

    @Test
    void execute_returnPriceNotFoundException() {
        logger.info("execute_returnPriceNotFoundException()");
        Long idBranch = 1L;
        Long idProduct = 1L;
        LocalDateTime dateTime = LocalDateTime.of(2020, 12, 31, 10, 0);

        when(priceRepository.findByBrandIdAndProductIdAndDate(idBranch, idProduct, dateTime))
                .thenReturn(List.of());

        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, () ->
                priceService.execute(idBranch, idProduct, dateTime)
        );
        assertEquals(DomainErrorMessage.PRICE_NOT_COMBINE.getMessage(), exception.getMessage());
    }

    @Test
    void execute_returnDomainValidationException_whenPriceOutOfRange() {
        logger.info("testExecute_PriceNotFoundException_WhenNoPricesFound()");
        Price price1 = mock(Price.class);
        when(price1.isDateInRange(any(LocalDateTime.class))).thenReturn(false);
        List<Price> priceList = List.of(price1);
        Long idBranch = 1L;
        Long idProduct = 1L;
        LocalDateTime dateTime = LocalDateTime.of(2020, 12, 31, 10, 0);

        when(priceRepository.findByBrandIdAndProductIdAndDate(idBranch, idProduct, dateTime))
                .thenReturn(priceList);

        DomainValidationException exception = assertThrows(DomainValidationException.class, () ->
                priceService.execute(idBranch, idProduct, dateTime)
        );
        assertEquals(PRICE_NOT_VALID_RANGE.getMessage(), exception.getMessage());
    }

    @Test
    void execute_returnDomainValidationException_whenDuplicateMaxPriority() {
        logger.info("execute_returnDomainValidationException_whenDuplicateMaxPriority()");
        Long brandId = 3L;
        Long productId = 4334L;
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 10, 10, 0);
        Price price1 = new Price(brandId, dateTime, dateTime, 1L, productId, 1, new BigDecimal("33.2"), Currency.EUR);
        List<Price> priceList = List.of(price1, price1);

        when(priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, dateTime))
                .thenReturn(priceList);

        DomainValidationException exception = assertThrows(DomainValidationException.class, () ->
                priceService.execute(brandId, productId, dateTime)
        );
        assertEquals(PRICE_DUPLICATE_MAX_PRIORITY.getMessage(), exception.getMessage());
    }
}