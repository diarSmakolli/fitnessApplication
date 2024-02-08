package com.example.fitness_app.ProgressTracking;

import com.example.fitness_app.common.CommonResponseDTO;

import java.util.Date;
import java.util.List;

public interface ProgressTrackingService {
    List<ProgressTrackingDTO> getProgressesByExerciseIdAndUserId(String exerciseId, String userId);
    ProgressTrackingEntity findProgressById(String id);
    ProgressTrackingEntity deleteProgress(String id, String userId);
    CommonResponseDTO<ProgressTrackingDTO> getAllProgresses(int pageNo, int pageSize, String sortBy, String sortDirection);
    ProgressTrackingDTO updateProgresses(String id, ProgressTrackingDTOSave progressTrackingDTO, String userId);
    ProgressTrackingDTO saveProgress(ProgressTrackingDTOSave progressDTO);
//    ProgressTrackingDTO saveProgressByExerciseIdAndUserId(ProgressTrackingDTO progressDTO,String exerciseId, String userId);
    ProgressTrackingDTO saveProgressByExerciseId(ProgressTrackingDTOSave progressDTO, String exerciseSessionId);
    List<ProgressTrackingDTO> getProgressesByExerciseId(String exerciseId);
//    ProgressTrackingDTO saveProgressByExerciseIdAndUserId(ProgressTrackingDTOSave progressTrackingDTO, String exerciseSessionId, String userId);
    ProgressTrackingDTO saveProgressByExerciseIdAndUserId(ProgressTrackingDTOSave progressTrackingDTO, String exerciseSessionId, String userId);
    List<ProgressTrackingDTO> getProgressesByDate(Date createdAt);
    List<ProgressTrackingDTO> getProgressesByBmiRange(Integer minBmi, Integer maxBmi);
}
