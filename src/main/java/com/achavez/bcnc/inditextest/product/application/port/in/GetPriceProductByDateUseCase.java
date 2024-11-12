package com.achavez.bcnc.inditextest.product.application.port.in;

import com.achavez.bcnc.inditextest.product.domain.Price;

import java.time.LocalDateTime;

public interface GetPriceProductByDateUseCase {

    Price execute(Long brandId, Long productId, LocalDateTime localDateTime);

}
