package com.example.fitness_app.ExerciseSession;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Builder
public class ExerciseSessionDTO {

    private String id;

    private String activityType;

    private Integer difficultyLevel;

    private Integer duration;

    private Double distance;

    private Double weight;

    private String notes;

    @Nullable
    private Date createdAt;

    @Nullable
    private String createdBy;

    @Nullable
    private Date updatedAt;

    @Nullable
    private String updatedBy;

    @Nullable
    private Date deletedAt;

    @Nullable
    private String deletedBy;

    private String userId;


}