package com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.controller;

import com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.dto.ProductPriceRateResponseDTO;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.error.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.Month;

import static com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.controller.GetPriceProductByDateController.API_V_1_PRICES;
import static org.junit.jupiter.api.Assertions.*;

@RestTestConfig
class GetPriceProductByDateControllerTest {

    private static final Logger logger = LogManager.getLogger(GetPriceProductByDateControllerTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At10hJune14() {
        logger.info("getPriceProductByDate_returnBy35455AndBrand1At10hJune14(): start");
        Long brandId = 1L;
        Long productId = 35455L;
        Long priceList = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);

        evaluateGetPriceBy(applicationDate, brandId, productId, priceList);
        logger.info("getPriceProductByDate_returnBy35455AndBrand1At10hJune14(): finish");
    }

    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At16hJune14() {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At16hJune14(): start");
        Long brandId = 1L;
        Long productId = 35455L;
        Long priceList = 2L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 16, 0);

        evaluateGetPriceBy(applicationDate, brandId, productId, priceList);
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At16hJune14(): finish");
    }

    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14() {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14(): start");
        Long brandId = 1L;
        Long productId = 35455L;
        Long priceList = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 21, 0);

        evaluateGetPriceBy(applicationDate, brandId, productId, priceList);
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14(): finish");
    }

    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At10hJune15() {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14(): start");

        Long brandId = 1L;
        Long productId = 35455L;
        Long priceList = 3L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 15, 10, 0);

        evaluateGetPriceBy(applicationDate, brandId, productId, priceList);
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At10hJune15(): finish");
    }

    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At9hJune16() {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At9hJune16(): start");
        Long brandId = 1L;
        Long productId = 35455L;
        Long priceList = 4L;

        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 16, 9, 0);

        evaluateGetPriceBy(applicationDate, brandId, productId, priceList);
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At9hJune16(): finish");
    }

    private void evaluateGetPriceBy(LocalDateTime applicationDate, Long brandId, Long productId, Long priceList) {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(API_V_1_PRICES + "/brand/{brandId}/product/{productId}")
                        .queryParam("applicationDate", applicationDate)
                        .build(brandId, productId))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductPriceRateResponseDTO.class)
                .value(responseDTO -> {
                    assertEquals(productId, responseDTO.productId());
                    assertEquals(brandId, responseDTO.brandId());
                    assertNotNull(responseDTO.finalPrice());
                    assertEquals(priceList, responseDTO.priceList());
                    assertTrue(applicationDate.isBefore(responseDTO.endDate()));
                    assertTrue(applicationDate.isAfter(responseDTO.startDate()));
                });
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
                    assertNotNull(errorResponse);
                    assertNotNull(errorResponse.getError());
                });
        logger.info("getPriceProductByDate_priceNotFound(): finish");

    }
}