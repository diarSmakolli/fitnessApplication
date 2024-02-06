package com.example.fitness_app.ExerciseSession;

import com.example.fitness_app.common.CommonResponseDTO;

import java.util.List;

public interface ExerciseSessionService {
    ExerciseSessionEntity findExerciseById(String id);
    ExerciseSessionDTO saveExercise(ExerciseSessionDTOSave exerciseDTO);
    ExerciseSessionEntity deleteExercise(String id, String userId);
    CommonResponseDTO<ExerciseSessionDTO> getAllExercises(int pageNo, int pageSize, String sortBy, String sortDirection);
    ExerciseSessionDTO updateExercise(String id, ExerciseSessionDTOSave exerciseSessionDTO, String userId);
    ExerciseSessionDTO saveExerciseByUserId(ExerciseSessionDTOSave exerciseDTO, String userId);
    List<ExerciseSessionDTO> getAllExercisesByUserId(String userId);
    ExerciseSessionDTO getExerciseByIdAndUserId(String id, String userId);
    List<ExerciseSessionDTO> getExercisesByActivityTypeAndUserId(String activityType);
    List<ExerciseSessionDTO> getExercisesByMinDistance(Double distance);

}
