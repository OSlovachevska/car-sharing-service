package com.example.carsharingservice.controler;

import com.example.carsharingservice.dto.payment.PaymentRequestDto;
import com.example.carsharingservice.dto.payment.PaymentResponseDto;
import com.example.carsharingservice.mapper.PaymentMapper;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentCalculationService;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.StripeService;
import com.example.carsharingservice.service.UserService;
import com.stripe.model.checkout.Session;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(name = "/payments")
public class PaymentController {

    private final PaymentService paymentService;

    private final UserService userService;

    private final PaymentMapper paymentMapper;

    private final StripeService stripeService;

    private final PaymentCalculationService paymentCalculationService;

    @Operation(summary = "Get payment by user id ", description = "Get payment by user id ")
    @GetMapping
    public List<PaymentResponseDto> getByUserId(@RequestParam Long userId) {
        return paymentService.getByUserId(userId).stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Create payment", description = "Create payment")
    @PostMapping
    public PaymentResponseDto createPaymentSession(@Valid @RequestBody
                                                   PaymentRequestDto requestDto) {
        Payment payment = paymentMapper.toModel(requestDto);
        BigDecimal moneyToPay = paymentCalculationService.calculatePaymentAmount(payment);
        BigDecimal moneyToFine = paymentCalculationService.calculateFineAmount(payment);
        payment.setPaymentAmount(moneyToPay);
        Session session = stripeService.createPaymentSession(payment.getPaymentAmount(),
                moneyToFine, payment);
        payment.setPaymentSessionId(session.getId());
        payment.setPaymentUrl(session.getUrl());
        payment = paymentService.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Operation(summary = "Getting own payments",
            description = "Getting own payments")
    @GetMapping("/my-payments")
    public List<PaymentResponseDto> findAllMyPayments(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return paymentService.getByUserEmail(email).stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());

    }

    @Operation(summary = "Get after successful payment",
            description = "Get after successful payment")
    @GetMapping("/success/{id}")
    public PaymentResponseDto getSuccessed(@PathVariable Long id) {
        Payment payment = paymentService.getById(id);
        payment.setPaymentStatus(Payment.PaymentStatus.PAID);
        return paymentMapper.toDto(paymentService.save(payment));
    }

    @Operation(summary = "Get after canceling your payment",
            description = "Get after canceling your payment")
    @GetMapping("/cancel/{id}")
    public PaymentResponseDto getCanceled(@PathVariable Long id) {
        return paymentMapper.toDto(paymentService.save(paymentService.getById(id)));
    }

}
