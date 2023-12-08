package com.example.carsharingservice.mapper;

import com.example.carsharingservice.condig.MapperConfig;
import com.example.carsharingservice.dto.payment.PaymentRequestDto;
import com.example.carsharingservice.dto.payment.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)

public interface PaymentMapper {

    PaymentResponseDto toDto(Payment payment);

    Payment toModel(PaymentRequestDto paymentRequestDto);
}
