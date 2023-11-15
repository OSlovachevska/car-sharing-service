package com.example.carsharingservice.dto.rental;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalResponseDto {

    private Long id;

    private LocalDate rentalTime;

    private LocalDate returnTime;

    private LocalDate actualReturnDate;

    private Long carId;

    private Long userId;
}
