package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final UserService userService;

    private final RentalService rentalService;

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getById(Long id) {
        return paymentRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Can`t find payment by id:" + id)
        );
    }

    @Override
    public List<Payment> getByUserEmail(String userEmail) {
        return paymentRepository.findPaymentByRentalUserEmail(userEmail);
    }

    @Override
    public List<Payment> getByUserId(Long userId) {
        return paymentRepository.findPaymentByRentalUserId(userId);
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
}
