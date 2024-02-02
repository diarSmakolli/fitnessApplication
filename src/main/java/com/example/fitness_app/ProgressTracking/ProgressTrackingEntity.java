package com.example.fitness_app.ProgressTracking;

import com.example.fitness_app.ExerciseSession.ExerciseSessionEntity;
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
@Table(name = "progress_tracking")
@Entity
@Builder

public class ProgressTrackingEntity {
    @Id
    @Column(name = "id")
    private final String id = UUID.randomUUID().toString();

//    @ManyToOne
//    @JoinColumn(name = "exercise_session_id", nullable = false)
//    private ExerciseSessionEntity exerciseSession;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "body_mass_index")
    private Integer bodyMassIndex;

    @Column(name = "other_metrics")
    @Nullable
    private Date otherMetrics;

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

//    public void setExerciseSession(ExerciseSessionEntity exerciseSession) {
//        this.exerciseSession = exerciseSession;
//        exerciseSession.getProgressTrackings().add(this);
//    }


}
