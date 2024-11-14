package com.achavez.bcnc.inditextest.product.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(

        Long brandId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long priceList,
        Long productId,
        Integer priority,
        BigDecimal price,
        Currency currency
) {

    public Price {
        if (endDate.isBefore(startDate)) {
            throw new DomainValidationException(DomainErrorMessage.PRICE_ENDDATE_BEFORE_STARTDATE);
        }
    }

    public boolean isDateInRange(LocalDateTime date) {
        return isBeforeOrEqualTo(date) && isAfterOrEqualTo(date);
    }

    private boolean isBeforeOrEqualTo(LocalDateTime date) {
        return startDate.isBefore(date) || startDate.isEqual(date);
    }

    private boolean isAfterOrEqualTo(LocalDateTime date) {
        return endDate.isAfter(date) || endDate.isEqual(date);
    }

}
