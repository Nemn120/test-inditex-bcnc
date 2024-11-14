package com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.mapper;

import com.achavez.bcnc.inditextest.product.domain.Price;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.entity.PriceJpaEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PriceJpaMapper {


    Price toDomain(PriceJpaEntity priceJpaEntity);

    default Optional<Price> toDomain(Optional<PriceJpaEntity> price){
        return price.map(this::toDomain);
    }

}
