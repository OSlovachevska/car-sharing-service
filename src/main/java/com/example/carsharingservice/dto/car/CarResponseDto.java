package com.example.carsharingservice.dto.car;

import com.example.carsharingservice.model.Car;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarResponseDto {

    private Long id;

    private String model;

    private String brand;

    private Car.CarType carType;

    private int inventory;

    private BigDecimal dailyFree;

}
