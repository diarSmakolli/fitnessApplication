package com.example.fitness_app.ExerciseSession;

import com.example.fitness_app.Test.TestServiceImpl;
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




}
