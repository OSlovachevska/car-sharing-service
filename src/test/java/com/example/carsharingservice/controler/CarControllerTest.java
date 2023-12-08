package com.example.carsharingservice.controler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.carsharingservice.dto.car.CarRequestDto;
import com.example.carsharingservice.dto.car.CarResponseDto;
import com.example.carsharingservice.mapper.CarMapper;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {
    private static final Long CAR_ID = 1L;

    @Mock
    private CarService carService;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarController carController;

    private CarRequestDto carRequestDto;
    private CarResponseDto carResponseDto;
    private Car car;

    @BeforeEach
    void setUp() {
        carRequestDto = new CarRequestDto();
        carRequestDto.setBrand("Peugeot");
        carRequestDto.setModel("107");
        carRequestDto.setCarType(Car.CarType.HATCHBACK);
        carRequestDto.setDailyFree(BigDecimal.TEN);

        carResponseDto = new CarResponseDto();
        carResponseDto.setId(CAR_ID);
        carResponseDto.setBrand(carRequestDto.getBrand());
        carResponseDto.setModel(carRequestDto.getModel());
        carResponseDto.setCarType(carRequestDto.getCarType());
        carResponseDto.setDailyFree(carRequestDto.getDailyFree());

        car = new Car();
        car.setId(CAR_ID);
        car.setBrand(carRequestDto.getBrand());
        car.setModel(carRequestDto.getModel());
        car.setType(carRequestDto.getCarType());
        car.setDailyFee(carRequestDto.getDailyFree());
    }

    @Test
    @DisplayName("add car")
    void add_validCarRequestDto_ok() {
        given(carMapper.toModel(any(CarRequestDto.class))).willReturn(car);
        given(carService.add(any(Car.class))).willReturn(car);
        given(carMapper.toDto(any(Car.class))).willReturn(carResponseDto);

        Assertions.assertEquals(carResponseDto, carController.add(carRequestDto));

        then(carMapper).should().toModel(carRequestDto);
        then(carService).should().add(car);
        then(carMapper).should().toDto(car);
    }

    @Test
    @DisplayName("get all cars")
    void getAll_notEmptyDB_ok() {
        given(carService.getAll()).willReturn(Collections.singletonList(car));
        given(carMapper.toDto(any(Car.class))).willReturn(carResponseDto);
        List<CarResponseDto> actual = carController.getAll();

        then(carService).should().getAll();

        List<CarResponseDto> expected = Collections.singletonList(carResponseDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("get all cars")
    void getAll_emptyDb_ok() {
        given(carService.getAll()).willReturn(Collections.emptyList());
        List<CarResponseDto> actual = carController.getAll();

        then(carService).should().getAll();

        List<CarResponseDto> expected = Collections.emptyList();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("delete car")
    void delete_validId_ok() {
        Mockito.doNothing().when(carService).delete(CAR_ID);

        carController.delete(CAR_ID);

        Mockito.verify(carService).delete(CAR_ID);
    }
}
