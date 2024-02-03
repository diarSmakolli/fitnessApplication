package com.example.fitness_app.ProgressTracking;
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
public class ProgressTrackingDTO {

    private String id;

    private Double weight;

    private Integer bodyMassIndex;

    @Nullable
    private Date otherMetrics;

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

    private String exerciseSessionId;

//    private String userId;

}
