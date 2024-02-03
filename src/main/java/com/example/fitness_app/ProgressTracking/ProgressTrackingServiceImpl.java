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

    private final UserRepository userRepository;

    public ProgressTrackingServiceImpl(ProgressTrackingMapper progressTrackingMapper,
                                       ProgressTrackingRepository progressTrackingRepository,
                                       ExerciseSessionService exerciseSessionService,
                                       ExerciseSessionRepository exerciseSessionRepository,
                                       UserRepository userRepository
    ) {
        this.progressTrackingMapper = progressTrackingMapper;
        this.progressTrackingRepository = progressTrackingRepository;
        this.exerciseSessionService = exerciseSessionService;
        this.exerciseSessionRepository = exerciseSessionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<ProgressTrackingDTO> getProgressesByExerciseIdAndUserId(String exerciseId, String userId) {
        List<ProgressTrackingEntity> progressEntities = progressTrackingRepository.findAllByExerciseIdAndUserId(exerciseId, userId);
        return progressEntities.stream()
                .map(progressTrackingMapper::mapToDTO)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public ProgressTrackingEntity deleteProgress(String id) {
        try {
            ProgressTrackingEntity existingProgress = findProgressById(id);

            existingProgress.setDeletedAt(new Date());

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
    public ProgressTrackingDTO updateProgresses(String id, ProgressTrackingDTOSave progressTrackingDTO) {
        ProgressTrackingEntity existingProgress = findProgressById(id);

        existingProgress.setWeight(progressTrackingDTO.getWeight());
        existingProgress.setBodyMassIndex(progressTrackingDTO.getBodyMassIndex());

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
