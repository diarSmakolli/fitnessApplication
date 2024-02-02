package com.example.fitness_app.ExerciseSession;

import com.example.fitness_app.Test.TestServiceImpl;
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
public class ExerciseSessionServiceImpl implements ExerciseSessionService {

    private static final Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

    private final ExerciseSessionMapper exerciseSessionMapper;

    private final ExerciseSessionRepository exerciseSessionRepository;

    public ExerciseSessionServiceImpl(ExerciseSessionMapper exerciseSessionMapper, ExerciseSessionRepository exerciseSessionRepository) {
        this.exerciseSessionMapper = exerciseSessionMapper;
        this.exerciseSessionRepository = exerciseSessionRepository;
    }


    @Override
    @Transactional
    public ExerciseSessionDTO saveExercise(ExerciseSessionDTOSave exerciseDTO) {
        try {
            ExerciseSessionEntity exercise = exerciseSessionMapper.mapToEntity(exerciseDTO);
            ExerciseSessionEntity savedExercise = exerciseSessionRepository.save(exercise);
            return exerciseSessionMapper.mapToDTO(savedExercise);
        } catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error saving test", ex);
            return null;
        } catch (BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public ExerciseSessionDTO updateExercise(String id, ExerciseSessionDTOSave exerciseSessionDTO) {
        ExerciseSessionEntity existingExercise = findExerciseById(id);

        existingExercise.setActivityType(exerciseSessionDTO.getActivityType());
        existingExercise.setDuration(exerciseSessionDTO.getDuration());
        existingExercise.setDistance(exerciseSessionDTO.getDistance());
        existingExercise.setWeight(exerciseSessionDTO.getWeight());
        existingExercise.setNotes(exerciseSessionDTO.getNotes());

        ExerciseSessionEntity updatedExercise = exerciseSessionRepository.save(existingExercise);

        logger.info("Exercise updated successfully!" + id);
        return exerciseSessionMapper.mapToDTO(updatedExercise);


    }

    @Override
    @Transactional
    public CommonResponseDTO<ExerciseSessionDTO> getAllExercises(int pageNo, int pageSize, String sortBy, String sortDirection) {
        ValidationUtilsDTO.validatePageParameters(pageNo, pageSize);
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ExerciseSessionEntity> exerciseSessionPage = exerciseSessionRepository.findAllByDeletedAtIsNull(pageable);

        if (exerciseSessionPage.isEmpty()) {
            logAndThrowEntityNotFoundException("No exercises found");
        }

        List<ExerciseSessionDTO> ExerciseSessionDTOs = exerciseSessionPage.getContent().stream()
                .map(exerciseSessionMapper::mapToDTO)
                .collect(Collectors.toList());


        return buildCommonResponse(ExerciseSessionDTOs, exerciseSessionPage);
    }

    @Override
    @Transactional
    public ExerciseSessionEntity deleteExercise(String id) {
        try {
            ExerciseSessionEntity existingExercise = findExerciseById(id);

            existingExercise.setDeletedAt(new Date());

            exerciseSessionRepository.save(existingExercise);


            logger.info("Exercise deleted successfully: " + id);
            return existingExercise;
        } catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error deleting exercise", ex);
        } catch (EntityNotFoundException ex) {
            logAndThrowEntityNotFoundException("Exercise not found: " + id);
        }
        return null;
    }

    @Override
    public ExerciseSessionEntity findExerciseById(String id) {
        ExerciseSessionEntity exercise = exerciseSessionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warning("Exercise not found: " + id);
                    return new EntityNotFoundException("Exercise not found");
                });

//        if(exercise.getDeletedAt() != null) {
//            logger.warning("Exercise has been deleted: " + id);
//            throw new EntityNotFoundException("Exercise has been deleted");
//        }

        checkExerciseStatusIsDeleted(exercise, id);

        return exercise;
    }

    private void checkExerciseStatusIsDeleted(ExerciseSessionEntity exercise, String id) {
        if(exercise.getDeletedAt() != null) {
            logger.warning("Exercise has been deleted: " + id);
            throw new EntityNotFoundException("Exercise has been deleted");
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

    private CommonResponseDTO<ExerciseSessionDTO> buildCommonResponse(List<ExerciseSessionDTO> testDTOs, Page<ExerciseSessionEntity> exerciseSessionPage) {

        return CommonResponseDTO.<ExerciseSessionDTO>builder()
                .list(testDTOs)
                .totalItems(exerciseSessionPage.getTotalElements())
                .currentPage(exerciseSessionPage.getNumber())
                .pageNumber(exerciseSessionPage.getNumber())
                .pageSize(exerciseSessionPage.getSize())
                .build();
    }



}
