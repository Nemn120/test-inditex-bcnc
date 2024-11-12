package com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.repository;

import com.achavez.bcnc.inditextest.product.application.port.out.PriceRepository;
import com.achavez.bcnc.inditextest.product.domain.Price;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.entity.PriceJpaEntity;
import com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.mapper.PriceJpaMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PriceJpaRepository implements PriceRepository {

    private final PriceSpringDataRepository priceSpringDataRepository;

    private final PriceJpaMapper priceJpaMapper;

    public PriceJpaRepository(PriceSpringDataRepository priceSpringDataRepository, PriceJpaMapper priceJpaMapper) {
        this.priceSpringDataRepository = priceSpringDataRepository;
        this.priceJpaMapper = priceJpaMapper;
    }

    @Override
    public List<Price> findByBrandIdAndProductIdAndDate(Long brandId, Long productId, LocalDateTime date) {
        List<PriceJpaEntity> priceList = priceSpringDataRepository.findByBrandIdAndProductIdAndDate(brandId, productId, date);
        return priceJpaMapper.toDomain(priceList);
    }
}