package com.example.carsharingservice.dto;

import com.example.carsharingservice.model.Car;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarRequestDto {
    private String model;

    private String brand;

    private Car.CarType carType;

    private BigDecimal dailyFree;

}
