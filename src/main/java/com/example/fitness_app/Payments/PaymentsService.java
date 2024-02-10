package com.example.fitness_app.Payments;

public interface PaymentsService {
    PaymentsEntity findPaymentById(String id);
    PaymentsDTO createPayment(PaymentsDTOSave paymentsDTO, String userId);
}
