package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {
    private static final String USD = "usd";
    private static final Long QUANTITY = 1L;

    @Value("${stripe.test.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe-domen}")
    private String domen;

    private final PaymentService paymentService;

    @Override
    public Session createPaymentSession(BigDecimal payment,
                                        BigDecimal fine, Payment paymentObject) {

        Stripe.apiKey = stripeSecretKey;
        Payment savedPayment = paymentService.save(paymentObject);
        Long paymentId = savedPayment.getId();
        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(domen + "/payments/success/" + paymentId)
                .setCancelUrl(domen + "/patments/cancel/" + paymentId)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(USD)
                                                .setUnitAmount((long) (payment.doubleValue() * 100))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData
                                                                .ProductData
                                                                .builder()
                                                                .setName(paymentObject
                                                                        .getPaymentType()
                                                                        .name())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                );
        if (fine.doubleValue() > 0) {
            paramsBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(QUANTITY)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency(USD)
                                            .setUnitAmount((long) (fine.doubleValue() * 100))
                                            .setProductData(
                                                    SessionCreateParams
                                                            .LineItem.PriceData
                                                            .ProductData.builder()
                                                            .setName(Payment
                                                                    .PaymentType.FINE.name())
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );

        }
        SessionCreateParams params = paramsBuilder.build();
        try {
            return Session.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Can`t create session with params" + params, e);

        }
    }
}

