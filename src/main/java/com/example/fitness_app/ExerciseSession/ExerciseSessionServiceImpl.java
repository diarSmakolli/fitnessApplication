package com.example.fitness_app.ExerciseSession;

import com.example.fitness_app.Test.TestServiceImpl;
import com.example.fitness_app.User.UserEntity;
import com.example.fitness_app.User.UserRepository;
import com.example.fitness_app.common.CommonResponseDTO;
import com.example.fitness_app.common.ValidationUtilsDTO;
import com.example.fitness_app.exceptions.BadRequestException;
import com.example.fitness_app.exceptions.EntityNotFoundException;
import com.example.fitness_app.exceptions.InternalServerErrorException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ExerciseSessionServiceImpl implements ExerciseSessionService {

    private static final Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

    private final ExerciseSessionMapper exerciseSessionMapper;

    private final ExerciseSessionRepository exerciseSessionRepository;

    private final UserRepository userRepository;

    public ExerciseSessionServiceImpl(ExerciseSessionMapper exerciseSessionMapper, ExerciseSessionRepository exerciseSessionRepository, UserRepository userRepository) {
        this.exerciseSessionMapper = exerciseSessionMapper;
        this.exerciseSessionRepository = exerciseSessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<ExerciseSessionDTO> getExercisesByActivityTypeAndUserId(String activityType) {
        try {
            List<ExerciseSessionEntity> exercisesByActivityType = exerciseSessionRepository.findByActivityTypeContainingIgnoreCaseAndDeletedAtIsNull(activityType);

            if(exercisesByActivityType.isEmpty()) {
                throw new NotFoundException("No exercises found with activity type: " + activityType);
            }

            return exercisesByActivityType.stream().map(exerciseSessionMapper::mapToDTO).collect(Collectors.toList());
        } catch(DataAccessException ex) {
            logAndThrowInternalServerError("Error retrieving exercises by activity type", ex);
            return Collections.emptyList();
        } catch(BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public List<ExerciseSessionDTO> getExercisesByMinDistance(Double distance) {
        try {
            List<ExerciseSessionEntity> exerciseByMinDistance = exerciseSessionRepository.findByDistanceGreaterThanEqual(distance);

            if(exerciseByMinDistance.isEmpty()) {
                throw new NotFoundException("No exercises found with distance greater than or equal to: " + distance);
            }

            return exerciseByMinDistance.stream()
                    .map(exerciseSessionMapper::mapToDTO)
                    .collect(Collectors.toList());

        } catch(DataAccessException ex) {
            logAndThrowInternalServerError("Error retrieving exercises by distance", ex);
            return Collections.emptyList();
        } catch(BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public List<ExerciseSessionDTO> getExercisesByDuration(Integer duration) {
        try {
            List<ExerciseSessionEntity> exercisesByDuration = exerciseSessionRepository.findByDuration(duration);

            if(exercisesByDuration.isEmpty()) {
                throw new NotFoundException("No exercises found with duration: " + duration);
            }

            return exercisesByDuration.stream()
                    .map(exerciseSessionMapper::mapToDTO)
                    .collect(Collectors.toList());

        } catch(DataAccessException ex) {
            logAndThrowInternalServerError("Error retrieving exercises by duration", ex);
            return Collections.emptyList();
        } catch(BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public List<ExerciseSessionDTO> getExercisesByDifficulty(Integer difficultyLevel) {
        try {
            List<ExerciseSessionEntity> exercisesByDuration = exerciseSessionRepository.findByDifficultyLevel(difficultyLevel);

            if(exercisesByDuration.isEmpty()) {
                throw new NotFoundException("No exercises found with difficulty: " + difficultyLevel);
            }

            return exercisesByDuration.stream()
                    .map(exerciseSessionMapper::mapToDTO)
                    .collect(Collectors.toList());

        } catch(DataAccessException ex) {
            logAndThrowInternalServerError("Error retrieving exercises by difficulty", ex);
            return Collections.emptyList();
        } catch(BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public List<ExerciseSessionDTO> getExercisesByActivityTypeOrNotes(String search) {
        try {
            List<ExerciseSessionEntity> exercisesByDuration =
                    exerciseSessionRepository.findByActivityTypeContainingIgnoreCaseOrNotesContainingIgnoreCaseAndDeletedAtIsNull(search, search);

            if(exercisesByDuration.isEmpty()) {
                throw new NotFoundException("No exercises found with difficulty: " + search);
            }

            return exercisesByDuration.stream()
                    .map(exerciseSessionMapper::mapToDTO)
                    .collect(Collectors.toList());

        } catch(DataAccessException ex) {
            logAndThrowInternalServerError("Error retrieving exercises by difficulty", ex);
            return Collections.emptyList();
        } catch(BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return Collections.emptyList();
        }
    }



    @Override
    @Transactional
    public ExerciseSessionDTO saveExerciseByUserId(ExerciseSessionDTOSave exerciseDTO, String userId) {
        try {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found" + userId));

            ExerciseSessionEntity exercise = exerciseSessionMapper.mapToEntity(exerciseDTO);
            exercise.setUser(user);
            exercise.setCreatedAt(new Date());
            exercise.setCreatedBy(user.getFirstname());

            ExerciseSessionEntity savedExercise = exerciseSessionRepository.save(exercise);
            return exerciseSessionMapper.mapToDTO(savedExercise);
        } catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error saving exercise", ex);
            return null;
        } catch (BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return null;
        }
    }


    @Override
    @Transactional
    public ExerciseSessionDTO getExerciseByIdAndUserId(String id, String userId) {

        try {
            ExerciseSessionEntity exercise = exerciseSessionRepository.findExerciseByIdAndUserIdAndDeletedAtIsNull(id, userId);


            if (exercise == null) {
                logAndThrowEntityNotFoundException("Exercise not found with id: " + id + " and userId: " + userId);
            }

            return exerciseSessionMapper.mapToDTO(exercise);
        }
        catch(DataAccessException ex) {
            logAndThrowInternalServerError("Error retrieving exercise", ex);
            return null;
        }
    }


    @Override
    @Transactional
    public List<ExerciseSessionDTO> getAllExercisesByUserId(String userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));


        List<ExerciseSessionEntity> exerciseSessions = exerciseSessionRepository.findAllByUserIdAndDeletedAtIsNull(userId);


        List<ExerciseSessionDTO> exerciseSessionDTOs = exerciseSessions.stream()
                .map(exerciseSessionMapper::mapToDTO)
                .collect(Collectors.toList());

        return exerciseSessionDTOs;
    }



    @Override
    @Transactional
    public ExerciseSessionDTO saveExercise(ExerciseSessionDTOSave exerciseDTO) {
        try {
            ExerciseSessionEntity exercise = exerciseSessionMapper.mapToEntity(exerciseDTO);
            exercise.setCreatedAt(new Date());
            ExerciseSessionEntity savedExercise = exerciseSessionRepository.save(exercise);
            return exerciseSessionMapper.mapToDTO(savedExercise);
        } catch (DataAccessException ex) {
            logAndThrowInternalServerError("Error saving Exercise", ex);
            return null;
        } catch (BadRequestException ex) {
            logAndThrowBadRequest("Invalid request: " + ex.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public ExerciseSessionDTO updateExercise(String id, ExerciseSessionDTOSave exerciseSessionDTO, String userId) {
        ExerciseSessionEntity existingExercise = findExerciseById(id);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found" + userId));

        existingExercise.setActivityType(exerciseSessionDTO.getActivityType());
        existingExercise.setDuration(exerciseSessionDTO.getDuration());
        existingExercise.setDistance(exerciseSessionDTO.getDistance());
        existingExercise.setWeight(exerciseSessionDTO.getWeight());
        existingExercise.setNotes(exerciseSessionDTO.getNotes());
        existingExercise.setUpdatedAt(new Date());
        existingExercise.setUser(user);
        existingExercise.setUpdatedBy(user.getFirstname());

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
    public ExerciseSessionEntity deleteExercise(String id, String userId) {
        try {

            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found" + userId));


            ExerciseSessionEntity existingExercise = findExerciseById(id);

            existingExercise.setDeletedAt(new Date());
            existingExercise.setUser(user);
            existingExercise.setDeletedBy(user.getFirstname());

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


    // simple function to export the data to an excel file for findall.
    public List<ExerciseSessionEntity> exportExerciseToExcel(HttpServletResponse response) throws IOException {
        List<ExerciseSessionEntity> exercises = exerciseSessionRepository.findAll();
        ExcelUtils exportUtils = new ExcelUtils(exercises);
        exportUtils.exportDataToExcel(response);
        return exercises;
    }






}
