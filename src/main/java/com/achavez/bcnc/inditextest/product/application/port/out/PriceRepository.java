package com.achavez.bcnc.inditextest.product.application.port.out;

import com.achavez.bcnc.inditextest.product.domain.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findTopWithHighestPriority(Long brandId, Long productId, LocalDateTime date);

}
