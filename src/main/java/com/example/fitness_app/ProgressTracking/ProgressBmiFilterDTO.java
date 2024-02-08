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
public class ProgressBmiFilterDTO {
    private Integer minBmi;
    private Integer maxBmi;
}
