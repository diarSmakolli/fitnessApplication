package com.example.fitness_app.Payments;

import org.springframework.stereotype.Component;

@Component
public class PaymentsMapper {

    public PaymentsDTO mapToDTO(PaymentsEntity paymentsEntity) {
        return PaymentsDTO.builder()
            .id(paymentsEntity.getId())
            .amount(paymentsEntity.getAmount())
            .paymentDate(paymentsEntity.getPaymentDate())
            .status(paymentsEntity.getStatus())
            .createdAt(paymentsEntity.getCreatedAt())
            .createdBy(paymentsEntity.getCreatedBy())
            .updatedAt(paymentsEntity.getUpdatedAt())
            .updatedBy(paymentsEntity.getUpdatedBy())
            .deletedAt(paymentsEntity.getDeletedAt())
            .deletedBy(paymentsEntity.getDeletedBy())
            .build();
    }


    public PaymentsEntity mapToEntity(PaymentsDTOSave paymentsDTO) {
        return PaymentsEntity.builder()
        .amount(paymentsDTO.getAmount())
        .build();
    }
        


}
