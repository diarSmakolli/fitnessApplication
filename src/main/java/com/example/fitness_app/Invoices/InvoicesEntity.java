package com.example.fitness_app.Invoices;

import com.example.fitness_app.Payments.PaymentsEntity;
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
@Table(name = "invoices")
@Entity
@Builder
public class InvoicesEntity {

    @Id
    @Column(name = "id")
    private final String id = UUID.randomUUID().toString();

    @Column(name = "amount")
    private double amount;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "status")
    private String status;

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
    @JoinColumn(name = "payment_id")
    private PaymentsEntity payment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
