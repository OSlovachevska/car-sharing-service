package com.example.carsharingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Data
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType type;

    private int inventory;

    @Column(nullable = false)
    private BigDecimal dailyFee;

    public enum CarType {
        SEDAN,
        SUV,
        HATCHBACK,
        UNIVERSAL
    }

}
