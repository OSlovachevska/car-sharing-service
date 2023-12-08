package com.example.carsharingservice.model;

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

    private String model;

    private String brand;

    @Enumerated(EnumType.STRING)
    private CarType carType;

    private int inventory;

    private BigDecimal dailyFree;

    public enum CarType {
        SEDAN,
        SUV,
        HATCHBACK,
        UNIVERSAL
    }

}
