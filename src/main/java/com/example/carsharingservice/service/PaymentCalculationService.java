package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import java.math.BigDecimal;

public interface PaymentCalculationService {

    BigDecimal calculatePaymentAmount(Payment payment);

    BigDecimal calculateFineAmount(Payment payment);
}
