package com.example.carsharingservice.dto.payment;

import com.example.carsharingservice.model.Payment;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {

    private Payment.PaymentStatus paymentStatus;

    private Payment.PaymentType paymentType;

    private Long rentalId;

    private BigDecimal paymentAmount;
}
