package com.example.fitness_app.ProgressTracking;

import com.example.fitness_app.common.CommonResponseDTO;

import java.util.List;

public interface ProgressTrackingService {
    List<ProgressTrackingDTO> getProgressesByExerciseIdAndUserId(String exerciseId, String userId);
    ProgressTrackingEntity findProgressById(String id);
    ProgressTrackingEntity deleteProgress(String id);
    CommonResponseDTO<ProgressTrackingDTO> getAllProgresses(int pageNo, int pageSize, String sortBy, String sortDirection);
    ProgressTrackingDTO updateProgresses(String id, ProgressTrackingDTOSave progressTrackingDTO);
    ProgressTrackingDTO saveProgress(ProgressTrackingDTOSave progressDTO);
//    ProgressTrackingDTO saveProgressByExerciseIdAndUserId(ProgressTrackingDTO progressDTO,String exerciseId, String userId);
    ProgressTrackingDTO saveProgressByExerciseId(ProgressTrackingDTOSave progressDTO, String exerciseSessionId);
}
