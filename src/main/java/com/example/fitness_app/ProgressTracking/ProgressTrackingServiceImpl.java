package com.example.fitness_app.ProgressTracking;
import com.example.fitness_app.ExerciseSession.ExerciseSessionDTO;
import com.example.fitness_app.ExerciseSession.ExerciseSessionEntity;
import com.example.fitness_app.ExerciseSession.ExerciseSessionRepository;
import com.example.fitness_app.ExerciseSession.ExerciseSessionService;
import com.example.fitness_app.Test.TestServiceImpl;
import com.example.fitness_app.User.UserEntity;
import com.example.fitness_app.User.UserRepository;
import com.example.fitness_app.common.CommonResponseDTO;
import com.example.fitness_app.common.ValidationUtilsDTO;
import com.example.fitness_app.exceptions.BadRequestException;
import com.example.fitness_app.exceptions.EntityNotFoundException;
import com.example.fitness_app.exceptions.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProgressTrackingServiceImpl implements ProgressTrackingService {

    private static final Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

    private final ProgressTrackingMapper progressTrackingMapper;

    private final ProgressTrackingRepository progressTrackingRepository;

    private final ExerciseSessionRepository exerciseSessionRepository;

    private final ExerciseSessionService exerciseSessionService;

    public ProgressTrackingServiceImpl(ProgressTrackingMapper progressTrackingMapper,
                                       ProgressTrackingRepository progressTrackingRepository,
                                       ExerciseSessionService exerciseSessionService,
                                       ExerciseSessionRepository exerciseSessionRepository
    ) {
        this.progressTrackingMapper = progressTrackingMapper;
        this.progressTrackingRepository = progressTrackingRepository;
        this.exerciseSessionService = exerciseSessionService;
        this.exerciseSessionRepository = exerciseSessionRepository;
    }


    @Override
    @Transactional
    public ProgressTrackingDTO saveProgress(ProgressTrackingDTOSave progressDTO, String exerciseSessionId, String userId) {
        ProgressTrackingEntity progressEntity = progressTrackingMapper.mapToEntity(progressDTO);

        ExerciseSessionEntity exerciseSessionEntity = exerciseSessionRepository.findExerciseByIdAndUserIdAndDeletedAtIsNull(exerciseSessionId, userId);

        progressEntity.setExerciseSession(exerciseSessionEntity);

        ProgressTrackingEntity savedProgress = progressTrackingRepository.save(progressEntity);

        return progressTrackingMapper.mapToDTO(savedProgress);
    }




}
