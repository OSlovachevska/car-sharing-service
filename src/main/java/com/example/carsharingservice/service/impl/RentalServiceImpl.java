package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;

    private final CarService carService;

    private final UserService userService;

    @Override
    public Rental createNewRental(Car car, User user, LocalDate startDate, LocalDate returnDate) {
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(user);
        rental.setRentalTime(startDate);
        rental.setReturnTime(returnDate);
        carService.delete(car.getId());
        rentalRepository.save(rental);
        return rental;
    }

    @Override
    public List<Rental> getRentalsByUserIdAndIsReturned(Long userId) {
        List<Rental> rental = rentalRepository.getByUserId(userId);
        return rental
                .stream()
                .filter(rental1 -> rental1.getActualReturnDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Rental getById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find rental by id " + id));
    }

    @Override
    public Rental returnCar(Long rentalId) {
        Rental rental = getById(rentalId);
        rental.setActualReturnDate(LocalDate.now());
        carService.addCarToInventory(rental.getCar().getId());
        rentalRepository.save(rental);
        return rental;
    }

    @Override
    public List<Rental> findByOverdueRent(LocalDate date) {
        return rentalRepository.findByOverdueRent(date);
    }

    @Override
    public void save(Rental rental) {
        rentalRepository.save(rental);
    }
}
