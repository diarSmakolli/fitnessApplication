package com.example.fitness_app.ExerciseSession;

import com.example.fitness_app.common.CommonResponseDTO;

import java.util.List;

public interface ExerciseSessionService {
    ExerciseSessionEntity findExerciseById(String id);
    ExerciseSessionDTO saveExercise(ExerciseSessionDTOSave exerciseDTO);
    ExerciseSessionEntity deleteExercise(String id);
    CommonResponseDTO<ExerciseSessionDTO> getAllExercises(int pageNo, int pageSize, String sortBy, String sortDirection);

    ExerciseSessionDTO updateExercise(String id, ExerciseSessionDTOSave exerciseSessionDTO);
    ExerciseSessionDTO saveExerciseByUserId(ExerciseSessionDTOSave exerciseDTO, String userId);
    List<ExerciseSessionDTO> getAllExercisesByUserId(String userId);

    ExerciseSessionDTO getExerciseByIdAndUserId(String exerciseId, String userId);

}
