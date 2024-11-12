package com.achavez.bcnc.inditextest.product.infrastructure.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.achavez.bcnc.inditextest.product"})
@EnableJpaRepositories(basePackages = "com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db")
@EntityScan(basePackages = "com.achavez.bcnc.inditextest.product.infrastructure.adapter.out.persistence.db.jpa.entity")
public class SpringBootRun {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRun.class, args);
    }

}
