package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import java.util.List;

public interface PaymentService {

    Payment save(Payment payment);

    Payment getById(Long id);

    List<Payment> getByUserEmail(String userEmail);

    List<Payment> getByUserId(Long userId);

    List<Payment> findAll();
}
