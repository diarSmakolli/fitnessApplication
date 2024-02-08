package com.example.fitness_app.ExerciseSession;

import org.springframework.stereotype.Component;

@Component
public class ExerciseSessionMapper {

    public ExerciseSessionDTO mapToDTO(ExerciseSessionEntity exerciseSessionEntity) {
        return ExerciseSessionDTO.builder()
                .id(exerciseSessionEntity.getId())
                .activityType(exerciseSessionEntity.getActivityType())
                .difficultyLevel(exerciseSessionEntity.getDifficultyLevel())
                .duration(exerciseSessionEntity.getDuration())
                .distance(exerciseSessionEntity.getDistance())
                .weight(exerciseSessionEntity.getWeight())
                .notes(exerciseSessionEntity.getNotes())
                .createdAt(exerciseSessionEntity.getCreatedAt())
                .createdBy(exerciseSessionEntity.getCreatedBy())
                .updatedAt(exerciseSessionEntity.getUpdatedAt())
                .updatedBy(exerciseSessionEntity.getUpdatedBy())
                .deletedAt(exerciseSessionEntity.getDeletedAt())
                .deletedBy(exerciseSessionEntity.getDeletedBy())
                .build();
    }

    public ExerciseSessionEntity mapToEntity(ExerciseSessionDTOSave exerciseSessionDTO) {
        return ExerciseSessionEntity.builder()
                .activityType(exerciseSessionDTO.getActivityType())
                .difficultyLevel(exerciseSessionDTO.getDifficultyLevel())
                .duration(exerciseSessionDTO.getDuration())
                .distance(exerciseSessionDTO.getDistance())
                .weight(exerciseSessionDTO.getWeight())
                .notes(exerciseSessionDTO.getNotes())
                .build();
    }
}