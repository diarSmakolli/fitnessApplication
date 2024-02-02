package com.example.fitness_app.ProgressTracking;
import com.example.fitness_app.ExerciseSession.ExerciseSessionDTO;
import com.example.fitness_app.ExerciseSession.ExerciseSessionDTOSave;
import com.example.fitness_app.ExerciseSession.ExerciseSessionEntity;
import org.springframework.stereotype.Component;

@Component
public class ProgressTrackingMapper {

    public ProgressTrackingDTO mapToDTO(ProgressTrackingEntity progressTrackingEntity) {
        return ProgressTrackingDTO.builder()
                .id(progressTrackingEntity.getId())
                .weight(progressTrackingEntity.getWeight())
                .bodyMassIndex(progressTrackingEntity.getBodyMassIndex())
                .otherMetrics(progressTrackingEntity.getOtherMetrics())
                .createdAt(progressTrackingEntity.getCreatedAt())
                .createdBy(progressTrackingEntity.getCreatedBy())
                .updatedAt(progressTrackingEntity.getUpdatedAt())
                .updatedBy(progressTrackingEntity.getUpdatedBy())
                .deletedAt(progressTrackingEntity.getDeletedAt())
                .deletedBy(progressTrackingEntity.getDeletedBy())
                .build();
    }

    public ProgressTrackingEntity mapToEntity(ProgressTrackingDTOSave progressTrackingDTO) {
        return ProgressTrackingEntity.builder()
                .weight(progressTrackingDTO.getWeight())
                .bodyMassIndex(progressTrackingDTO.getBodyMassIndex())
                .build();
    }




}
