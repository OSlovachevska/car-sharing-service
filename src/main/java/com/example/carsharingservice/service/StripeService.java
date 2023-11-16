package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import com.stripe.model.checkout.Session;
import java.math.BigDecimal;

public interface StripeService {

    Session createPaymentSession(BigDecimal payment, BigDecimal fine, Payment paymentObject);

}
