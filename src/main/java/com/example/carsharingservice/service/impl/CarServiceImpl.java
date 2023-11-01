package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.repository.CarRepository;
import com.example.carsharingservice.service.CarService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public Car add(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car get(Long id) {
        return carRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Can`t find car by id"));
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Car update(Long id, Car car) {
        Car carFromDb = get(id);
        carFromDb.setModel(car.getModel());
        carFromDb.setBrand(car.getBrand());
        carFromDb.setInventory(car.getInventory());
        carFromDb.setDailyFree(car.getDailyFree());
        carFromDb.setCarType(car.getCarType());
        return carRepository.save(carFromDb);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}
