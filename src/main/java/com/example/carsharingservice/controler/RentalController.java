package com.example.carsharingservice.controler;

import com.example.carsharingservice.dto.rental.RentalRequestDto;
import com.example.carsharingservice.dto.rental.RentalResponseDto;
import com.example.carsharingservice.mapper.RentalMapper;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/rentals")
public class RentalController {

    private final RentalService rentalService;

    private final UserService userService;

    private final CarService carService;

    private final RentalMapper rentalMapper;

    @GetMapping("/{id}")
    @Operation(description = "Get rental by id")
    public RentalResponseDto getSpecificRental(@PathVariable Long id) {
        return rentalMapper.toDto(rentalService.getById(id));
    }

    @GetMapping
    @Operation(description = "Get rentals by user id and is active")
    public List<RentalResponseDto> getActiveRental(Authentication authentication) {
        return rentalService.getRentalsByUserIdAndIsReturned(
                        userService.getByUsername(authentication.getName()).getId())
                .stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/return")
    @Operation(description = "End rental by id")
    public void returnCar(@PathVariable Long id) {
        Rental rental = rentalService.returnCar(id);
    }

    @PostMapping
    @Operation(description = "Create new rental")
    public RentalResponseDto createRental(Authentication authentication,
                                          @RequestBody RentalRequestDto rentalRequestDto) {
        checkCorrectDate(rentalRequestDto.getRentalTime(), rentalRequestDto.getReturnTime());
        Car car = carService.get(rentalRequestDto.getCarId());
        User user = userService.getByUsername(authentication.getName());
        Rental newRental =
                rentalService.createNewRental(car, user,
                        rentalRequestDto.getRentalTime(), rentalRequestDto.getReturnTime());
        return rentalMapper.toDto(newRental);
    }

    private void checkCorrectDate(LocalDate start, LocalDate end) {
        if (Period.between(start, end).isNegative()) {
            throw new RuntimeException("Period of rent can't be negative");
        }
    }
}
