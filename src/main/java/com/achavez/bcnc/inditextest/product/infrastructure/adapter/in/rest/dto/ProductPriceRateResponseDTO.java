package com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.dto;

import java.time.LocalDateTime;

public record ProductPriceRateResponseDTO(
        Long productId,
        Long brandId,
        Long priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String finalPrice
) {
}
