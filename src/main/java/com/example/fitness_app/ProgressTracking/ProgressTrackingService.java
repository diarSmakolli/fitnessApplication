package com.example.fitness_app.ProgressTracking;

import com.example.fitness_app.common.CommonResponseDTO;
public interface ProgressTrackingService {
    ProgressTrackingDTO saveProgress(ProgressTrackingDTOSave progressDTO, String exerciseSessionId, String userId);
}
