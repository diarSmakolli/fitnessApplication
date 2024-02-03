package com.example.fitness_app.ProgressTracking;
import com.example.fitness_app.ExerciseSession.ExerciseSessionDTO;
import com.example.fitness_app.ExerciseSession.ExerciseSessionDTOSave;
import com.example.fitness_app.ExerciseSession.ExerciseSessionEntity;
import com.example.fitness_app.common.CommonResponseDTO;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/progress")
@Validated
public class ProgressTrackingController {

    private final ProgressTrackingService progressTrackingService;

    @Autowired
    public ProgressTrackingController(ProgressTrackingService progressTrackingService) {
        this.progressTrackingService = progressTrackingService;
    }


    @GetMapping("/exercise/{exerciseId}/user/{userId}")
    public ResponseEntity<List<ProgressTrackingDTO>> getProgressesByExerciseIdAndUserId(
            @PathVariable String exerciseId,
            @PathVariable String userId) {
        List<ProgressTrackingDTO> progressDTOs = progressTrackingService.getProgressesByExerciseIdAndUserId(exerciseId, userId);
        return new ResponseEntity<>(progressDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved progress by ID")
    @ApiResponse(responseCode = "404", description = "Progress not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<ProgressTrackingEntity> findById(@PathVariable String id) {
        ProgressTrackingEntity progressTrackingDTO = progressTrackingService.findProgressById(id);
        return ResponseEntity.ok(progressTrackingDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ApiResponse(responseCode = "404", description = "Progress not found")
    @ApiResponse(responseCode = "200", description = "Successfully deleted progress")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<ProgressTrackingEntity> deleteProgress(@PathVariable String id) {
        ProgressTrackingEntity deletedProgressTracking = progressTrackingService.deleteProgress(id);
        if (deletedProgressTracking != null) {
            return ResponseEntity.ok(deletedProgressTracking);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/getAll")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all progresses")
    @ApiResponse(responseCode = "404", description = "No progresses found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<CommonResponseDTO<ProgressTrackingDTO>> getAllProgresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortProperty,
            @RequestParam(defaultValue = "asc") String order) {
        CommonResponseDTO<ProgressTrackingDTO> responseDTO = progressTrackingService.getAllProgresses(page, size, sortProperty, order);
        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping(value = "/{id}")
    @ApiResponse(responseCode = "200", description = "Progress updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "404", description = "Progress not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<ProgressTrackingDTO> updateProgress(@PathVariable String id, @RequestBody ProgressTrackingDTOSave progressTrackingDTO) {

        ProgressTrackingDTO updatedProgressTrackingDTO = progressTrackingService.updateProgresses(id, progressTrackingDTO);
        return ResponseEntity.ok(updatedProgressTrackingDTO);

    }

    @PostMapping(value = "/save")
    @ApiResponse(responseCode = "201", description = "Progress created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<ProgressTrackingDTO> savedProgress(@RequestBody ProgressTrackingDTOSave progressDTO)
    {
        ProgressTrackingDTO savedProgress = progressTrackingService.saveProgress(progressDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedProgress);
    }





}
