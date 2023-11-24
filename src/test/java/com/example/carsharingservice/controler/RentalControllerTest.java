package com.example.carsharingservice.controler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.carsharingservice.dto.rental.RentalRequestDto;
import com.example.carsharingservice.dto.rental.RentalResponseDto;
import com.example.carsharingservice.mapper.RentalMapper;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

class RentalControllerTest {

    @Mock
    private RentalService rentalService;

    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    @Mock
    private RentalMapper rentalMapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private RentalController rentalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSpecificRental() {
        Long rentalId = 1L;
        Rental rental = new Rental();
        when(rentalService.getById(rentalId)).thenReturn(rental);
        when(rentalMapper.toDto(rental)).thenReturn(new RentalResponseDto());

        RentalResponseDto responseDto = rentalController.getSpecificRental(rentalId);

        assertNotNull(responseDto);
        verify(rentalService, times(1)).getById(rentalId);
        verify(rentalMapper, times(1)).toDto(rental);
    }

    @Test
    void getActiveRental() {
        Long userId = 1L;
        when(authentication.getName()).thenReturn("testUser");
        User user = new User();
        user.setId(userId);
        when(userService.getByUsername("testUser")).thenReturn(user);

        when(rentalService.getRentalsByUserIdAndIsReturned(userId))
                .thenReturn(Collections.singletonList(new Rental()));
        when(rentalMapper.toDto(any(Rental.class))).thenReturn(new RentalResponseDto());

        List<RentalResponseDto> responseDtoList = rentalController.getActiveRental(authentication);

        assertNotNull(responseDtoList);
        assertFalse(responseDtoList.isEmpty());
        verify(rentalService, times(1)).getRentalsByUserIdAndIsReturned(userId);
        verify(rentalMapper, times(1)).toDto(any(Rental.class));
    }

    @Test
    void returnCar() {
        Long rentalId = 1L;
        Rental rental = new Rental();
        when(rentalService.returnCar(rentalId)).thenReturn(rental);

        rentalController.returnCar(rentalId);

        verify(rentalService, times(1)).returnCar(rentalId);
    }

    @Test
    void createRental() {
        when(authentication.getName()).thenReturn("testUser");
        when(userService.getByUsername("testUser")).thenReturn(new User());

        RentalRequestDto requestDto = new RentalRequestDto();
        requestDto.setCarId(1L);
        requestDto.setRentalTime(LocalDate.now());
        requestDto.setReturnTime(LocalDate.now().plusDays(3));

        Car car = new Car();
        when(carService.get(requestDto.getCarId())).thenReturn(car);

        Rental newRental = new Rental();
        when(rentalService.createNewRental(car, new User(),
                requestDto.getRentalTime(), requestDto.getReturnTime()))
                .thenReturn(newRental);

        when(rentalMapper.toDto(newRental)).thenReturn(new RentalResponseDto());

        RentalResponseDto responseDto = rentalController.createRental(authentication, requestDto);

        assertNotNull(responseDto);
        verify(userService, times(1)).getByUsername("testUser");
        verify(carService, times(1)).get(requestDto.getCarId());
        verify(rentalService, times(1)).createNewRental(car,
                new User(), requestDto.getRentalTime(), requestDto.getReturnTime());
        verify(rentalMapper, times(1)).toDto(newRental);
    }

    @Test
    void checkCorrectDate() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(3);

        Method method = RentalController.class
                .getDeclaredMethod("checkCorrectDate", LocalDate.class, LocalDate.class);
        method.setAccessible(true);

        assertDoesNotThrow(() -> method.invoke(rentalController, start, end));

        LocalDate invalidStart = LocalDate.now().plusDays(3);
        LocalDate invalidEnd = LocalDate.now();
        assertThrows(InvocationTargetException.class,
                () -> method.invoke(rentalController, invalidStart, invalidEnd));
    }
}
