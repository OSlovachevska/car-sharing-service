package com.example.carsharingservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.repository.CarRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    @DisplayName("Check if the car is in the database after saving")
    public void add_ValidCar_ReturnValidCar() {
        Car car = new Car();
        car.setId(1L);
        car.setType(Car.CarType.SEDAN);
        car.setModel("A6");
        car.setBrand("Audi");
        car.setInventory(3);
        car.setDailyFee(BigDecimal.valueOf(100));

        when(carRepository.save(car)).thenReturn(car);

        Car savedCar = carService.add(car);

        assertNotNull(savedCar);
        assertEquals(car.getId(), savedCar.getId());
        assertEquals(car.getInventory(), savedCar.getInventory());
        assertEquals(car.getType(), savedCar.getType());
        assertEquals(car.getModel(), savedCar.getModel());
        assertEquals(car.getBrand(), savedCar.getBrand());
        assertEquals(car.getDailyFee(), car.getDailyFee());

        verify(carRepository, times(1)).save(car);
    }

    @Test
    @DisplayName("Find car by id")
    public void get_ValidId_ReturnValidCarById() {
        Car car = new Car();
        car.setId(1L);
        car.setType(Car.CarType.SEDAN);
        car.setModel("A6");
        car.setBrand("Audi");
        car.setInventory(3);
        car.setDailyFee(BigDecimal.valueOf(100));
        Long carId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Car actual = carService.get(carId);
        assertNotNull(actual);
        assertEquals(car.getId(), actual.getId());
        assertEquals(car.getInventory(), actual.getInventory());
        assertEquals(car.getType(), actual.getType());
        assertEquals(car.getModel(), actual.getModel());
        assertEquals(car.getBrand(), actual.getBrand());
        assertEquals(car.getDailyFee(), car.getDailyFee());

        verify(carRepository, times(1)).findById(carId);
    }

    @Test
    @DisplayName("Find all cars")
    public void getAll_ValidCars_ReturnOneCar() {
        Car car = new Car();
        car.setId(1L);
        car.setType(Car.CarType.SEDAN);
        car.setModel("A6");
        car.setBrand("Audi");
        car.setInventory(3);
        car.setDailyFee(BigDecimal.valueOf(100));
        List<Car> allCars = new ArrayList<>();

        allCars.add(car);

        when(carService.getAll()).thenReturn(allCars);

        List<Car> actual = carService.getAll();

        assertNotNull(actual);
        assertEquals(allCars, actual);

        verify(carRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Update cars")
    public void update_ValidCars_ReturnOneCar() {
        Car car = new Car();
        car.setId(1L);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        Car updatedCar = carService.update(car);
        assertNotNull(updatedCar);
        assertEquals(car, updatedCar);
    }

    @Test
    @DisplayName("Delete caar by id")
    public void delete_DeleteCarById() {
        Long carId = 1L;
        carService.delete(carId
        );
    }

}
