package com.example.fitness_app.ProgressTracking;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgressTrackingDTOSave {
    private Double weight;
    private Integer bodyMassIndex;
    private Double muscleMass;
    private Double fatPercentage;
    private Double waterPercentage;
}
