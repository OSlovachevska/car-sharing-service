package com.example.carsharingservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class PaymentServiceImplTest {

    private static final Long PAYMENT_ID = 1L;
    private static final String USER_EMAIL = "test@gmail.com";
    private static final Long USER_ID = 1L;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save Payment")
    public void should_SavePayment() {
        Payment payment = new Payment();
        payment.setId(PAYMENT_ID);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment savedPayment = paymentService.save(payment);

        assertEquals(PAYMENT_ID, savedPayment.getId());
        verify(paymentRepository, Mockito.times(1)).save(eq(payment));
    }

    @Test
    @DisplayName("Get Payment by ID")
    public void should_GetPaymentById() {
        Payment payment = new Payment();
        payment.setId(PAYMENT_ID);

        when(paymentRepository.findById(PAYMENT_ID)).thenReturn(java.util.Optional.of(payment));

        Payment foundPayment = paymentService.getById(PAYMENT_ID);

        assertEquals(PAYMENT_ID, foundPayment.getId());
        verify(paymentRepository, Mockito.times(1)).findById(eq(PAYMENT_ID));
    }
}
