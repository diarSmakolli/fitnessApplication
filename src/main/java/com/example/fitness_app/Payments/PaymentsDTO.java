package com.example.fitness_app.Payments;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import com.example.fitness_app.Payments.PaymentsEntity.PaymentStatus;
import com.example.fitness_app.User.UserDTO;
import com.example.fitness_app.User.UserEntity;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PaymentsDTO {

    private String id;

    private double amount;

    private Date paymentDate;

    private PaymentsEntity.PaymentStatus status;

    private Integer dueDate;

    private Integer issueDate;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

    private Date deletedAt;

    private String deletedBy;

    private String userId;

    private UserDTO user;

}