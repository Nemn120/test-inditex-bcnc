package com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.repository;

import com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.entity.PriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceSpringDataRepository extends JpaRepository<PriceJpaEntity, Long> {

    @Query(value = """
                SELECT p.*
                FROM Prices p
                WHERE p.brand_id = :brandId
                  AND p.product_id = :productId
                  AND p.start_date <= :date
                  AND p.end_date >= :date
                ORDER BY p.priority DESC
                LIMIT 1
            """, nativeQuery = true)
    Optional<PriceJpaEntity> findTopWithHighestPriority(
            @Param("brandId") Long brandId,
            @Param("productId") Long productId,
            @Param("date") LocalDateTime date
    );
}
