package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;

public interface NotificationService {

    void sendMessageAboutSuccessRent(Rental rental);

    void checkOverdueRentals();

    void sendMessageToAllUsers(String message);

    void sendMessageAboutSuccessfulPayment(Long rentalId, Long chatId);

    void sendMessageWithPaymentUrl(String url, Long chatId);
}
