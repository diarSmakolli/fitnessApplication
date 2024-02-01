package com.example.fitness_app.ExerciseSession;
import com.example.fitness_app.Test.TestEntity;
import com.example.fitness_app.Test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseSessionEntity> findById(@PathVariable String id) {
        ExerciseSessionEntity exerciseSessionDTO = exerciseSessionService.findExerciseById(id);
        return ResponseEntity.ok(exerciseSessionDTO);
    }




}
