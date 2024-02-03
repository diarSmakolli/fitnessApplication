package com.example.fitness_app.ProgressTracking;
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


    @PostMapping("/save")
    @ApiResponse(responseCode = "201", description = "Progress saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public ResponseEntity<ProgressTrackingDTO> saveProgress(@RequestBody ProgressTrackingDTOSave progressDTO,
                                                            @RequestParam String exerciseSessionId,
                                                            @RequestParam String userId) {
        try {
            ProgressTrackingDTO savedProgress = progressTrackingService.saveProgress(progressDTO, exerciseSessionId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProgress);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
