package com.example.fitness_app.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tests")
@Validated
public class TestController {
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<TestDTO> saveTest(@RequestBody TestDTOSave testDTO) {
        TestDTO savedTestDTO = testService.saveTest(testDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedTestDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestEntity> findById(@PathVariable String id) {
        TestEntity testDTO = testService.findTestById(id);
        return ResponseEntity.ok(testDTO);
    }



}
