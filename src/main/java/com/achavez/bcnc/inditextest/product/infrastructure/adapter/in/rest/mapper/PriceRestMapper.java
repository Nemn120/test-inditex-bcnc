package com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.mapper;

import com.achavez.bcnc.inditextest.product.domain.Price;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.dto.ProductPriceRateResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PriceRestMapper {

    @Mapping(target = "finalPrice", source = ".", qualifiedByName = "finalPrice")
    ProductPriceRateResponseDTO toDto(Price price);

    @Named("finalPrice")
    default String finalPrice(Price source) {
        return source.price().toString() + " " + source.currency().getValue();
    }

}
