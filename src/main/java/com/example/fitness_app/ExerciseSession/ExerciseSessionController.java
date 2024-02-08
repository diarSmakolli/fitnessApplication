package com.example.fitness_app.ExerciseSession;

import com.example.fitness_app.common.CommonResponseDTO;
import com.example.fitness_app.exceptions.InternalServerErrorException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.fitness_app.exceptions.BadRequestException;
import com.example.fitness_app.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exercises")
@Validated
public class ExerciseSessionController {

    private final ExerciseSessionService exerciseSessionService;

    @Autowired
    public ExerciseSessionController(ExerciseSessionService exerciseSessionService) {

        this.exerciseSessionService = exerciseSessionService;
    }

    @GetMapping("/export-to-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Exercises_information.xlsx";
        response.setHeader(headerKey, headerValue);
        exerciseSessionService.exportExerciseToExcel(response);
    }



    @GetMapping("/getActivityType")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exercise by ID")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ExerciseSessionDTO>> getExerciseByActivityType(@RequestParam String activityType) {
        List<ExerciseSessionDTO> exerciseActivityTypeList = exerciseSessionService.getExercisesByActivityTypeAndUserId(activityType);
        return ResponseEntity.ok(exerciseActivityTypeList);
    }

    @GetMapping("/getDistanceByMin")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exercise by ID and user ID")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ExerciseSessionDTO>> getExerciseByMinDistance(@RequestParam Double distance) {
            List<ExerciseSessionDTO> exercises = exerciseSessionService.getExercisesByMinDistance(distance);
            return ResponseEntity.ok(exercises);
    }

    @GetMapping("/getByDuration")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exercise by ID and user ID")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ExerciseSessionDTO>> getExercisesByDuration(@RequestParam Integer duration) {
        List<ExerciseSessionDTO> exercises = exerciseSessionService.getExercisesByDuration(duration);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/getByDifficultyLevel")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exercise by ID and user ID")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ExerciseSessionDTO>> getExercisesByDifficultyLevel(@RequestParam Integer difficultyLevel) {
        List<ExerciseSessionDTO> exercises = exerciseSessionService.getExercisesByDifficulty(difficultyLevel);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/search")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exercise by ID and user ID")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ExerciseSessionDTO>> getExercisesByDifficultyLevel
            (@RequestParam String search) {
        List<ExerciseSessionDTO> exercises = exerciseSessionService.getExercisesByActivityTypeOrNotes(search);
        return ResponseEntity.ok(exercises);
    }


    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved test by ID")
    @ApiResponse(responseCode = "404", description = "Test not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ExerciseSessionEntity> findById(@PathVariable String id) {
        ExerciseSessionEntity exerciseSessionDTO = exerciseSessionService.findExerciseById(id);
        return ResponseEntity.ok(exerciseSessionDTO);
    }

    @GetMapping("/get/user/{userId}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exercise by ID")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<ExerciseSessionDTO>> getAllExercisesByUserId(@RequestParam String userId) {

        List<ExerciseSessionDTO> exerciseSessionList = exerciseSessionService.getAllExercisesByUserId(userId);
        return ResponseEntity.ok(exerciseSessionList);
    }

    @GetMapping("/get/{id}/user/{userId}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved exercise by ID and user ID")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ExerciseSessionDTO> getExerciseByIdAndUserId(
            @RequestParam String id,
            @RequestParam String userId) {

        ExerciseSessionDTO exercise = exerciseSessionService.getExerciseByIdAndUserId(id, userId);

        if (exercise != null) {
            return ResponseEntity.ok(exercise);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping(value = "/save")
    @ApiResponse(responseCode = "201", description = "Test created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<ExerciseSessionDTO> saveExercise(@RequestBody ExerciseSessionDTOSave exerciseDTO)
    {
        ExerciseSessionDTO savedExercise = exerciseSessionService.saveExercise(exerciseDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedExercise);
    }


    @PostMapping(value = "/save/user/{userId}")
    @ApiResponse(responseCode = "201", description = "Test created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<ExerciseSessionDTO> saveExerciseByUserId(@RequestBody ExerciseSessionDTOSave exerciseSessionDTObyUserId, @RequestParam String userId) {

        if(userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ExerciseSessionDTO savedExerciseByUserId = exerciseSessionService.saveExerciseByUserId(exerciseSessionDTObyUserId, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedExerciseByUserId);

    }


    @PutMapping(value = "/{id}/user/{userId}")
    @ApiResponse(responseCode = "200", description = "Exercise updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<ExerciseSessionDTO> updateExercise(@PathVariable String id, @RequestBody ExerciseSessionDTOSave exerciseSessionDTO, @RequestParam String userId) {

        ExerciseSessionDTO updatedExerciseSessionDTO = exerciseSessionService.updateExercise(id, exerciseSessionDTO, userId);
        return ResponseEntity.ok(updatedExerciseSessionDTO);

    }

    @DeleteMapping(value = "/{id}/user/{userId}")
    @ApiResponse(responseCode = "404", description = "Exercise not found")
    @ApiResponse(responseCode = "200", description = "Successfully deleted exercise")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<ExerciseSessionEntity> deleteExercise(@PathVariable String id, @RequestParam String userId) {
        if(userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ExerciseSessionEntity deletedExercise = exerciseSessionService.deleteExercise(id, userId);
        if (deletedExercise != null) {
            return ResponseEntity.ok(deletedExercise);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/getAll")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all exercises")
    @ApiResponse(responseCode = "404", description = "No exercises found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<CommonResponseDTO<ExerciseSessionDTO>> getAllExercises(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortProperty,
            @RequestParam(defaultValue = "asc") String order) {
        CommonResponseDTO<ExerciseSessionDTO> responseDTO = exerciseSessionService.getAllExercises(page, size, sortProperty, order);
        return ResponseEntity.ok(responseDTO);
    }





}
