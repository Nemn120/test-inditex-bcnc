package com.achavez.bcnc.inditextest.product.application.service;

import com.achavez.bcnc.inditextest.product.application.port.in.GetPriceProductByDateUseCase;
import com.achavez.bcnc.inditextest.product.application.port.out.PriceRepository;
import com.achavez.bcnc.inditextest.product.domain.Price;
import com.achavez.bcnc.inditextest.product.domain.PriceNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.achavez.bcnc.inditextest.product.domain.DomainErrorMessage.PRICE_NOT_COMBINE;

public class GetPriceProductByDateService implements GetPriceProductByDateUseCase {

    private final PriceRepository priceRepository;

    public GetPriceProductByDateService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price execute(Long idBranch, Long idProduct, LocalDateTime dateTime) {
        Optional<Price> optionalPrice = priceRepository.findTopWithHighestPriority(idBranch, idProduct, dateTime);

        if(optionalPrice.isEmpty()){
            throw new PriceNotFoundException(PRICE_NOT_COMBINE);
        }
        return optionalPrice.get();
    }
}
