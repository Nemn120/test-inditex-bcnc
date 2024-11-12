package com.achavez.bcnc.inditextest.product.application.port.out;

import com.achavez.bcnc.inditextest.product.domain.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {

    List<Price> findByBrandIdAndProductIdAndDate(Long brandId, Long productId, LocalDateTime date);

}
