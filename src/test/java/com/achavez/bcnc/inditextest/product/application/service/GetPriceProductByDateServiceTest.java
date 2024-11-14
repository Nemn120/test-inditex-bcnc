package com.achavez.bcnc.inditextest.product.application.service;

import com.achavez.bcnc.inditextest.product.application.port.out.PriceRepository;
import com.achavez.bcnc.inditextest.product.domain.Currency;
import com.achavez.bcnc.inditextest.product.domain.DomainErrorMessage;
import com.achavez.bcnc.inditextest.product.domain.Price;
import com.achavez.bcnc.inditextest.product.domain.PriceNotFoundException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

        when(priceRepository.findTopWithHighestPriority(brandId, brandId, dateTime)).thenReturn(Optional.of(price));

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

        when(priceRepository.findTopWithHighestPriority(idBranch, idProduct, dateTime)).thenReturn(Optional.empty());

        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, () ->
                priceService.execute(idBranch, idProduct, dateTime)
        );
        assertEquals(DomainErrorMessage.PRICE_NOT_COMBINE.getMessage(), exception.getMessage());
    }
}