package com.example.carsharingservice.controler;

import com.example.carsharingservice.dto.car.CarRequestDto;
import com.example.carsharingservice.dto.car.CarResponseDto;
import com.example.carsharingservice.mapper.CarMapper;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    private final CarMapper carMapper;

    @PostMapping
    @Operation(description = "create new car if such don't exists")
    public CarResponseDto add(@RequestBody @Valid CarRequestDto carRequestDto) {
        Car car = carService.add(carMapper.toModel(carRequestDto));
        return carMapper.toDto(car);
    }

    @GetMapping("/{id}")
    @Operation(description = "get car by id")
    public CarResponseDto get(@PathVariable Long id) {
        return carMapper.toDto(carService.get(id));
    }

    @GetMapping
    @Operation(description = "get all cars")
    public List<CarResponseDto> getAll() {
        return carService.getAll().stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(description = "update car by id")
    public CarResponseDto update(@PathVariable Long id,
                                 @RequestBody @Valid CarRequestDto carRequestDto) {
        Car car = carMapper.toModel(carRequestDto);
        return carMapper.toDto(car);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "delete car by id")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }
}
