package com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.controller;

import com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.dto.ProductPriceRateResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.controller.GetPriceProductByDateController.API_V_1_PRICES;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RestTestConfig
class GetPriceProductByDateControllerTest {

    private static final Logger logger = LogManager.getLogger(GetPriceProductByDateControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At10hJune14() throws Exception {
        logger.info("getPriceProductByDate_returnBy35455AndBrand1At10hJune14(): start");

        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);

        ProductPriceRateResponseDTO responseDTO = getProductPriceRateResponseDTOByMockMvcResult(applicationDate);

        assertNotNull(responseDTO);
        assertEquals("35.50", responseDTO.finalPrice().toString());
        assertEquals(1L, responseDTO.priceList());
    }

    @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At16hJune14() throws Exception {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At16hJune14(): start");
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 16, 0);

        ProductPriceRateResponseDTO responseDTO = getProductPriceRateResponseDTOByMockMvcResult(applicationDate);

        assertNotNull(responseDTO);
        assertEquals("25.45", responseDTO.finalPrice().toString());
        assertEquals(2L, responseDTO.priceList());
    }

    @DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14() throws Exception {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14(): start");
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 14, 21, 0);

        ProductPriceRateResponseDTO responseDTO = getProductPriceRateResponseDTOByMockMvcResult(applicationDate);

        assertNotNull(responseDTO);
        assertEquals("35.50", responseDTO.finalPrice().toString());
        assertEquals(1L, responseDTO.priceList());
    }

    @DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At10hJune15() throws Exception {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At21hJune14(): start");
        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 15, 10, 0);

        ProductPriceRateResponseDTO responseDTO = getProductPriceRateResponseDTOByMockMvcResult(applicationDate);

        assertNotNull(responseDTO);
        assertEquals("30.50", responseDTO.finalPrice().toString());
        assertEquals(3L, responseDTO.priceList());
    }

    @DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
    @Test
    void getPriceProductByDate_returnPriceForProduct35455AndBrand1At9hJune16() throws Exception {
        logger.info("getPriceProductByDate_returnPriceForProduct35455AndBrand1At9hJune16(): start");

        LocalDateTime applicationDate = LocalDateTime.of(2020, Month.JUNE, 16, 9, 0);

        ProductPriceRateResponseDTO responseDTO = getProductPriceRateResponseDTOByMockMvcResult(applicationDate);

        assertNotNull(responseDTO);
        assertEquals("38.95", responseDTO.finalPrice().toString());
        assertEquals(4L, responseDTO.priceList());
    }

    private ProductPriceRateResponseDTO getProductPriceRateResponseDTOByMockMvcResult(LocalDateTime applicationDate) throws Exception {
        String formattedDate = applicationDate.format(DateTimeFormatter.ISO_DATE_TIME);

        MvcResult result = mockMvc.perform(get(API_V_1_PRICES + "/brand/{brandId}/product/{productId}", 1L, 35455L)
                        .param("applicationDate", formattedDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPrice", notNullValue()))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ProductPriceRateResponseDTO response = mapper.readValue(result.getResponse().getContentAsString(), ProductPriceRateResponseDTO.class);
        assertTrue(applicationDate.isBefore(response.endDate()));
        assertTrue(applicationDate.isAfter(response.startDate()));
        return response;
    }

    @Test
    void getPriceProductByDate_priceNotFound() throws Exception {
        logger.info("getPriceProductByDate_priceNotFound(): start");
        logger.info("getPriceProductByDate_priceNotFound(): Evaluate price not found in range dates");
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2024, Month.MARCH, 14, 10, 0);
        String formattedDate = applicationDate.format(DateTimeFormatter.ISO_DATE_TIME);

        mockMvc.perform(get(API_V_1_PRICES + "/brand/{brandId}/product/{productId}", brandId, productId)
                        .param("applicationDate", formattedDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error", notNullValue()))
                .andReturn();

        logger.info("getPriceProductByDate_priceNotFound(): finish");
    }
}