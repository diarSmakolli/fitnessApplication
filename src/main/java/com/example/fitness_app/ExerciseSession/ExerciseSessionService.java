package com.example.fitness_app.ExerciseSession;

import com.example.fitness_app.common.CommonResponseDTO;

public interface ExerciseSessionService {
    ExerciseSessionEntity findExerciseById(String id);
    ExerciseSessionDTO saveExercise(ExerciseSessionDTOSave exerciseDTO);
    ExerciseSessionEntity deleteExercise(String id);
    CommonResponseDTO<ExerciseSessionDTO> getAllExercises(int pageNo, int pageSize, String sortBy, String sortDirection);

    ExerciseSessionDTO updateExercise(String id, ExerciseSessionDTOSave exerciseSessionDTO);
}
