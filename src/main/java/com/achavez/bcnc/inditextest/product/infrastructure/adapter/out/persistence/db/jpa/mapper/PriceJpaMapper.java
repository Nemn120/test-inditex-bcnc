package com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.mapper;

import com.achavez.bcnc.inditextest.product.domain.Price;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.entity.PriceJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceJpaMapper {

    List<Price> toDomain(List<PriceJpaEntity> priceJpaEntity);

    Price toDomain(PriceJpaEntity priceJpaEntity);

}
