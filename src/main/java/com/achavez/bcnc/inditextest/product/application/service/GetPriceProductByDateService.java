package com.achavez.bcnc.inditextest.product.application.service;

import com.achavez.bcnc.inditextest.product.application.port.in.GetPriceProductByDateUseCase;
import com.achavez.bcnc.inditextest.product.application.port.out.PriceRepository;
import com.achavez.bcnc.inditextest.product.domain.DomainValidationException;
import com.achavez.bcnc.inditextest.product.domain.Price;
import com.achavez.bcnc.inditextest.product.domain.PriceNotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.achavez.bcnc.inditextest.product.domain.DomainErrorMessage.*;

public class GetPriceProductByDateService implements GetPriceProductByDateUseCase {

    private final PriceRepository priceRepository;

    public GetPriceProductByDateService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price execute(Long idBranch, Long idProduct, LocalDateTime dateTime) {
        List<Price> priceList = priceRepository.findByBrandIdAndProductIdAndDate(idBranch, idProduct, dateTime);
        if (priceList.isEmpty())
            throw new PriceNotFoundException(PRICE_NOT_COMBINE);
        evaluateDateInRangePrices(dateTime, priceList);
        return getPriceWithMaxPriorty(priceList);
    }

    private Price getPriceWithMaxPriorty(List<Price> priceList) {
        Optional<Price> maxPrice = priceList.stream()
                .max(Comparator.comparing(Price::priority));

        if (maxPrice.isPresent()) {
            long count = priceList.stream()
                    .filter(price -> price.equalsPriority(maxPrice.get().priority()))
                    .count();
            if (count > 1) {
                throw new DomainValidationException(PRICE_DUPLICATE_MAX_PRIORITY);
            }
            return maxPrice.get();
        }
        return null;
    }

    private void evaluateDateInRangePrices(LocalDateTime dateTime, List<Price> priceList) {
        priceList.stream()
                .filter(price -> !price.isDateInRange(dateTime))
                .findAny()
                .ifPresent(price -> {
                    throw new DomainValidationException(PRICE_NOT_VALID_RANGE);
                });
    }
}
