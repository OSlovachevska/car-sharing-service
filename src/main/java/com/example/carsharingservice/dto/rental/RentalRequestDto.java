package com.example.carsharingservice.dto.rental;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalRequestDto {

    private LocalDate rentalTime;

    private LocalDate returnTime;

    private Long carId;

    private Long userId;

}
