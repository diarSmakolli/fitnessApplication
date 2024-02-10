package com.example.fitness_app.Payments;

import com.example.fitness_app.User.UserEntity;
import lombok.*;
import org.apache.james.mime4j.dom.datetime.DateTime;
import org.springframework.lang.Nullable;
import java.time.LocalDateTime;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
@Entity
@Builder
public class PaymentsEntity {

    public enum PaymentStatus {
        PAID,
        PENDING,
        FAILED
    }

    @Id
    @Column(name = "id")
    private final String id = UUID.randomUUID().toString();

    @Column(name = "amount")
    private double amount;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "due_date")
    private Integer dueDate;

    @Column(name = "issue_date")
    private Integer issueDate;

    @Column(name = "created_at")
    @Nullable
    private Date createdAt;

    @Column(name = "created_by")
    @Nullable
    private String createdBy;

    @Column(name = "updated_at")
    @Nullable
    private Date updatedAt;

    @Column(name = "updated_by")
    @Nullable
    private String updatedBy;

    @Column(name = "deleted_at")
    @Nullable
    private Date deletedAt;

    @Column(name = "deleted_by")
    @Nullable
    private String deletedBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;



}

