package com.example.fitness_app.ProgressTracking;
import com.example.fitness_app.ExerciseSession.*;
import com.example.fitness_app.Test.TestServiceImpl;
import com.example.fitness_app.User.UserEntity;
import com.example.fitness_app.User.UserRepository;
import com.example.fitness_app.common.CommonResponseDTO;
import com.example.fitness_app.common.ValidationUtilsDTO;
import com.example.fitness_app.exceptions.BadRequestException;
import com.example.fitness_app.exceptions.EntityNotFoundException;
import com.example.fitness_app.exceptions.InternalServerErrorException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
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

    private final ExerciseSessionMapper exerciseSessionMapper;

    private final UserRepository userRepository;

    public ProgressTrackingServiceImpl(ProgressTrackingMapper progressTrackingMapper,
                                       ProgressTrackingRepository progressTrackingRepository,
                                       ExerciseSessionService exerciseSessionService,
                                       ExerciseSessionRepository exerciseSessionRepository,
                                       UserRepository userRepository,
                                       ExerciseSessionMapper exerciseSessionMapper
    ) {
        this.progressTrackingMapper = progressTrackingMapper;
        this.progressTrackingRepository = progressTrackingRepository;
        this.exerciseSessionService = exerciseSessionService;
        this.exerciseSessionRepository = exerciseSessionRepository;
        this.userRepository = userRepository;
        this.exerciseSessionMapper = exerciseSessionMapper;
    }


    @Override
    @Transactional
    public ProgressTrackingDTO saveProgressByExerciseId(ProgressTrackingDTOSave progressDTO, String exerciseSessionId) {
        try {
            ExerciseSessionEntity exercise = exerciseSessionRepository.findById(exerciseSessionId)
                    .orElseThrow(() -> new EntityNotFoundException("Exercise not found" + exerciseSessionId));

            ProgressTrackingEntity progress = progressTrackingMapper.mapToEntity(progressDTO);
            progress.setExerciseSession(exercise);
            progress.setCreatedAt(new Date());

            ProgressTrackingEntity savedProgress = progressTrackingRepository.save(progress);
            return progressTrackingMapper.mapToDTO(savedProgress);
        } catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error saving progress", ex);
            return null;
        } catch (BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public ProgressTrackingDTO saveProgressByExerciseIdAndUserId(ProgressTrackingDTOSave progressTrackingDTO, String exerciseSessionId, String userId) {
        try {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found" + userId));

            ExerciseSessionEntity exercise = exerciseSessionRepository.findById(exerciseSessionId)
                    .orElseThrow(() -> new EntityNotFoundException("Exercise not found" + exerciseSessionId));

            ProgressTrackingEntity progress = progressTrackingMapper.mapToEntity(progressTrackingDTO);
            progress.setUser(user);
            progress.setCreatedAt(new Date());
            progress.setCreatedBy(user.getFirstname());
            progress.setExerciseSession(exercise);

            ProgressTrackingEntity savedProgress = progressTrackingRepository.save(progress);
            return progressTrackingMapper.mapToDTO(savedProgress);
        } catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error saving progress", ex);
            return null;
        } catch (BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return null;
        }
    }

//    @Override
//    @Transactional
//    public ProgressTrackingDTO saveProgressByExerciseIdAndUserId(ProgressTrackingDTOSave progressTrackingDTO, String exerciseSessionId, String userId) {
//        try {
//            ExerciseSessionEntity exerciseSessionEntity = exerciseSessionRepository.findById(exerciseSessionId)
//                    .orElseThrow(() -> new EntityNotFoundException("Exercise not found" + exerciseSessionId));
//
//            UserEntity user = userRepository.findById(userId)
//                    .orElseThrow(() -> new EntityNotFoundException("User not found" + userId));
//
//            ProgressTrackingEntity progressTrackingEntity = progressTrackingMapper.mapToEntity(progressTrackingDTO);
//            progressTrackingEntity.setExerciseSession(exerciseSessionEntity);
//            progressTrackingEntity.setCreatedAt(new Date());
//
//            return null;
//        } catch(DataAccessException ex) {
//            logAndThrowInternalServerError("Error saving progress", ex);
//            return null;
//        } catch(BadRequestException ex) {
//            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
//            return null;
//        }
//    }



    @Override
    public List<ProgressTrackingDTO> getProgressesByExerciseIdAndUserId(String exerciseId, String userId) {
        List<ProgressTrackingEntity> progressEntities = progressTrackingRepository.findAllByExerciseIdAndUserId(exerciseId, userId);
        return progressEntities.stream()
                .map(progressTrackingMapper::mapToDTO)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<ProgressTrackingDTO> getProgressesByExerciseId(String exerciseId) {
//        List<ProgressTrackingEntity> progresses = progressTrackingRepository.findProgressesByExerciseIdAndDeletedAtIsNull(exerciseId);
//
//        return progresses.stream()
//                .map(progressTrackingMapper::mapToDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<ProgressTrackingDTO> getProgressesByExerciseId(String exerciseId) {
        List<ProgressTrackingEntity> progresses = progressTrackingRepository.findProgressesByExerciseIdAndDeletedAtIsNull(exerciseId);

        return progresses.stream()
                .map(progress -> {
                    ProgressTrackingDTO progressDTO = progressTrackingMapper.mapToDTO(progress);
                    // Set the exerciseSessionId directly in the DTO
                    progressDTO.setExerciseSessionId(progress.getExerciseSession().getId());
                    return progressDTO;
                })
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public ProgressTrackingDTO saveProgress(ProgressTrackingDTOSave progressDTO) {
        try {
            ProgressTrackingEntity progress = progressTrackingMapper.mapToEntity(progressDTO);
            progress.setCreatedAt(new Date());
            ProgressTrackingEntity savedProgress = progressTrackingRepository.save(progress);
            return progressTrackingMapper.mapToDTO(savedProgress);
        } catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error saving progress", ex);
            return null;
        } catch (BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return null;
        }
    }





    @Override
    @Transactional
    public ProgressTrackingEntity deleteProgress(String id, String userId) {
        try {
            ProgressTrackingEntity existingProgress = findProgressById(id);

            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found" + userId));


            existingProgress.setDeletedAt(new Date());
            existingProgress.setUser(user);
            existingProgress.setDeletedBy(user.getFirstname());


            progressTrackingRepository.save(existingProgress);

            logger.info("Progress deleted successfully: " + id);
            return existingProgress;
        } catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error deleting Progress", ex);
        } catch (EntityNotFoundException ex) {
            logAndThrowEntityNotFoundException("Progress not found: " + id);
        }
        return null;
    }

    @Override
    @Transactional
    public CommonResponseDTO<ProgressTrackingDTO> getAllProgresses(int pageNo, int pageSize, String sortBy, String sortDirection) {
        ValidationUtilsDTO.validatePageParameters(pageNo, pageSize);
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ProgressTrackingEntity> progressTrackingPage = progressTrackingRepository.findAllByDeletedAtIsNull(pageable);

        if (progressTrackingPage.isEmpty()) {
            logAndThrowEntityNotFoundException("No progresses found");
        }

        List<ProgressTrackingDTO> ProgressTrackingDTOS = progressTrackingPage.getContent().stream()
                .map(progressTrackingMapper::mapToDTO)
                .collect(Collectors.toList());


        return buildCommonResponse(ProgressTrackingDTOS, progressTrackingPage);
    }

//    @Override
//    @Transactional
//    public ProgressTrackingDTO saveProgress(ProgressTrackingDTOSave progressDTO) {
//        try {
//            ProgressTrackingEntity progress = progressTrackingMapper.mapToEntity(progressDTO);
//            progress.setCreatedAt(new Date());
//            ProgressTrackingEntity savedProgress = progressTrackingRepository.save(progress);
//            return progressTrackingMapper.mapToDTO(savedProgress);
//        } catch (DataAccessException ex) {
//            logAndThrowInternalServerError("Error saving progress", ex);
//            return null;
//        } catch (BadRequestException ex) {
//            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
//            return null;
//        }
//    }

    @Override
    @Transactional
    public ProgressTrackingDTO updateProgresses(String id, ProgressTrackingDTOSave progressTrackingDTO, String userId) {
        ProgressTrackingEntity existingProgress = findProgressById(id);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found" + userId));

        existingProgress.setWeight(progressTrackingDTO.getWeight());
        existingProgress.setBodyMassIndex(progressTrackingDTO.getBodyMassIndex());
        existingProgress.setUpdatedAt(new Date());
        existingProgress.setUser(user);
        existingProgress.setUpdatedBy(user.getFirstname());

        ProgressTrackingEntity updatedProgress = progressTrackingRepository.save(existingProgress);

        logger.info("Progress updated successfully!" + id);
        return progressTrackingMapper.mapToDTO(existingProgress);
    }





    @Override
    public ProgressTrackingEntity findProgressById(String id) {
        ProgressTrackingEntity progress = progressTrackingRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warning("Progress not found: " + id);
                    return new EntityNotFoundException("Progress not found");
                });

        checkProgressStatusIsDeleted(progress, id);

        return progress;
    }

    private void checkProgressStatusIsDeleted(ProgressTrackingEntity progress, String id) {
        if(progress.getDeletedAt() != null) {
            logger.warning("Progress has been deleted: " + id);
            throw new EntityNotFoundException("Progress has been deleted");
        }
    }


    private void logAndThrowEntityNotFoundException(String message) {
        logger.warning(message);
        throw new EntityNotFoundException(message);
    }

    private void logAndThrowInternalServerError(String message, Exception ex) {
        logger.severe(message + ": " + ex.getMessage());
        throw new InternalServerErrorException(message);
    }

    private void logAndThrowBadRequest(String message) {
        logger.warning(message);
        throw new BadRequestException(message);
    }

    private CommonResponseDTO<ProgressTrackingDTO> buildCommonResponse(List<ProgressTrackingDTO> progressTrackingDTOS, Page<ProgressTrackingEntity> progressTrackingPage) {

        return CommonResponseDTO.<ProgressTrackingDTO>builder()
                .list(progressTrackingDTOS)
                .totalItems(progressTrackingPage.getTotalElements())
                .currentPage(progressTrackingPage.getNumber())
                .pageNumber(progressTrackingPage.getNumber())
                .pageSize(progressTrackingPage.getSize())
                .build();
    }


}
