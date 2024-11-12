package com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.controller;

import com.achavez.bcnc.inditextest.product.application.port.in.GetPriceProductByDateUseCase;
import com.achavez.bcnc.inditextest.product.domain.Price;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.dto.ProductPriceRateResponseDTO;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.mapper.PriceRestMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(GetPriceProductByDateController.API_V_1_PRICES)
public class GetPriceProductByDateController {

    public static final String API_V_1_PRICES = "/api/v1/prices";
    private final GetPriceProductByDateUseCase getPriceProductByDateUseCase;
    private final PriceRestMapper priceRestMapper;

    public GetPriceProductByDateController(GetPriceProductByDateUseCase getPriceProductByDateUseCase, PriceRestMapper priceRestMapper) {
        this.getPriceProductByDateUseCase = getPriceProductByDateUseCase;
        this.priceRestMapper = priceRestMapper;
    }

    @GetMapping("/brand/{brandId}/product/{productId}")
    public ResponseEntity<ProductPriceRateResponseDTO> getPriceProductByDate(
            @PathVariable Long brandId,
            @PathVariable Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate
    ) {
        Price price = getPriceProductByDateUseCase.execute(brandId, productId, applicationDate);
        ProductPriceRateResponseDTO responseDTO = priceRestMapper.toDto(price);
        return ResponseEntity.ok(responseDTO);
    }

}
