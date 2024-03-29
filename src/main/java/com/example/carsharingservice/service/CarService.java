package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Car;
import java.util.List;

public interface CarService {

    Car add(Car car);

    Car get(Long id);

    List<Car> getAll();

    Car update(Car car);

    void delete(Long id);

    Car addCarToInventory(Long id);

    Car removeCarFromInventory(Long id);
}
