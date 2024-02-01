package com.example.fitness_app.Test;

public interface TestService {
    TestDTO saveTest(TestDTOSave testDTO);
    TestEntity findTestById(String id);
}
