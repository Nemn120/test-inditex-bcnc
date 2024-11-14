package com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.mapper;

import com.achavez.bcnc.inditextest.product.domain.Price;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.in.rest.dto.ProductPriceRateResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceRestMapper {

    @Mapping(target = "finalPrice", source = "price")
    ProductPriceRateResponseDTO toDto(Price price);

}
