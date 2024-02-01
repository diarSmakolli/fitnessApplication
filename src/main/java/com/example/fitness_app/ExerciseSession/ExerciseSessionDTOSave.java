package com.example.fitness_app.ExerciseSession;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Builder
public class ExerciseSessionDTOSave {


    private String activityType;

    private Integer duration;

    private Double distance;

    private Double weight;

    private String notes;


}