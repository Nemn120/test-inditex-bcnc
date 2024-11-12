package com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.repository;

import com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.entity.PriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceSpringDataRepository extends JpaRepository<PriceJpaEntity, Long> {

    @Query("""
        SELECT p
        FROM PriceJpaEntity p
        WHERE p.brandId = :brandId
          AND p.productId = :productId
          AND p.startDate <= :date
          AND p.endDate >= :date
    """)
    List<PriceJpaEntity> findByBrandIdAndProductIdAndDate(
            @Param("brandId") Long brandId,
            @Param("productId") Long productId,
            @Param("date") LocalDateTime date
    );
}
