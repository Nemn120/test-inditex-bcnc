package com.achavez.bcnc.inditextest.product.infrastructure.config;

import com.achavez.bcnc.inditextest.product.application.port.out.PriceRepository;
import com.achavez.bcnc.inditextest.product.application.service.GetPriceProductByDateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationServiceConfig {
    @Bean
    GetPriceProductByDateService getPriceProductByDateService(PriceRepository priceJpaRepository){
        return new GetPriceProductByDateService(priceJpaRepository);
    }

}
