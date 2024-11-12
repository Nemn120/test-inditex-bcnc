package com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.controller;

import com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.dto.ProductPriceRateResponseDTO;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.error.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.Month;

import static com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.controller.GetPriceProductByDateController.API_V_1_PRICES;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RestTestConfig
class GetPriceProductByDateControllerTest {

    private static final Logger logger = LogManager.getLogger(GetPriceProductByDateControllerTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At10hJune14() {
        logger.info("getPriceProductByDate_returnBy35455AndBrand1At10hJune14(): start");
      
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);

        EntityExchangeResult<ProductPriceRateResponseDTO> returnResult = getProductPriceRateResponseDTOEntityExchangeResult(applicationDate);

        ProductPriceRateResponseDTO responseDTO = returnResult.getResponseBody();
        assertNotNull(responseDTO);
        assertEquals("35.50 €", responseDTO.finalPrice());
        assertEquals(1L, responseDTO.priceList());
    }

    @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At16hJune14() {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At16hJune14(): start");
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 16, 0);

        EntityExchangeResult<ProductPriceRateResponseDTO> returnResult = getProductPriceRateResponseDTOEntityExchangeResult(applicationDate);

        ProductPriceRateResponseDTO responseDTO = returnResult.getResponseBody();
        assertNotNull(responseDTO);
        assertEquals("25.45 €", responseDTO.finalPrice());
        assertEquals(2L, responseDTO.priceList());
    }

    @DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14() {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14(): start");
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 21, 0);

        EntityExchangeResult<ProductPriceRateResponseDTO> returnResult = getProductPriceRateResponseDTOEntityExchangeResult(applicationDate);

        ProductPriceRateResponseDTO responseDTO = returnResult.getResponseBody();
        assertNotNull(responseDTO);
        assertEquals("35.50 €", responseDTO.finalPrice());
        assertEquals(1L, responseDTO.priceList());
    }

    @DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At10hJune15() {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14(): start");
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 15, 10, 0);

        EntityExchangeResult<ProductPriceRateResponseDTO> returnResult = getProductPriceRateResponseDTOEntityExchangeResult(applicationDate);

        ProductPriceRateResponseDTO responseDTO = returnResult.getResponseBody();
        assertNotNull(responseDTO);
        assertEquals("30.50 €", responseDTO.finalPrice());
        assertEquals(3L, responseDTO.priceList());
    }

    @DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At9hJune16() {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At9hJune16(): start");

        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 16, 9, 0);

        EntityExchangeResult<ProductPriceRateResponseDTO> returnResult = getProductPriceRateResponseDTOEntityExchangeResult(applicationDate);

        ProductPriceRateResponseDTO responseDTO = returnResult.getResponseBody();
        assertNotNull(responseDTO);
        assertEquals("38.95 €", responseDTO.finalPrice());
        assertEquals(4L, responseDTO.priceList());
    }

    private EntityExchangeResult<ProductPriceRateResponseDTO> getProductPriceRateResponseDTOEntityExchangeResult(LocalDateTime applicationDate) {
        return webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(API_V_1_PRICES + "/brand/{brandId}/product/{productId}")
                        .queryParam("applicationDate", applicationDate)
                        .build(1L, 35455L))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductPriceRateResponseDTO.class)
                .value(responseDTO -> {
                    assertTrue(applicationDate.isBefore(responseDTO.endDate()));
                    assertTrue(applicationDate.isAfter(responseDTO.startDate()));
                }).returnResult();
    }

    @Test
    void getPriceProductByDate_priceNotFound() {
        logger.info("getPriceProductByDate_priceNotFound(): start");
        logger.info("getPriceProductByDate_priceNotFound(): Evaluate price not found in range dates");
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2024, Month.MARCH, 14, 10, 0);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(API_V_1_PRICES + "/brand/{brandId}/product/{productId}")
                        .queryParam("applicationDate", applicationDate)
                        .build(brandId, productId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .consumeWith(response -> {
                    ErrorResponse errorResponse = response.getResponseBody();
                    assertNotNull(errorResponse);
                    assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatus());
                    assertNotNull(errorResponse.getError());
                });
        logger.info("getPriceProductByDate_priceNotFound(): finish");

    }
}