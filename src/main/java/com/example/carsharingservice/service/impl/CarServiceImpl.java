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
    public Car update(Car car) {
        Car carFromDb = carRepository.findById(car.getId()).orElseThrow(() ->
                new NoSuchElementException("Can't find car by id: " + car.getId()));
        carFromDb.setId(car.getId());
        carFromDb.setModel(car.getModel());
        carFromDb.setBrand(car.getBrand());
        carFromDb.setType(car.getType());
        carFromDb.setInventory(car.getInventory());
        carFromDb.setDailyFee(car.getDailyFee());
        return carFromDb;
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car addCarToInventory(Long id) {
        Car car = get(id);
        car.setInventory(car.getInventory() + 1);
        return carRepository.save(car);
    }

    @Override
    public Car removeCarFromInventory(Long id) {
        Car car = get(id);
        if (car.getInventory() > 0) {
            car.setInventory(car.getInventory() - 1);
            return carRepository.save(car);
        }
        throw new RuntimeException("Can't take car with id " + id + " from inventory");
    }
}
