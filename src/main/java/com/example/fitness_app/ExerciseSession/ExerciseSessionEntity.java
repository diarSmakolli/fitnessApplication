package com.example.fitness_app.ExerciseSession;

import lombok.*;
import org.apache.james.mime4j.dom.datetime.DateTime;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exercise_sessions")
@Entity
@Builder
public class ExerciseSessionEntity {

    @Id
    @Column(name = "id")
    private final String id = UUID.randomUUID().toString();

    @Column(name = "activity_type")
    private String activityType;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "notes")
    private String notes;

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
}
