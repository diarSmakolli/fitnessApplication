package com.example.fitness_app.Test;

import org.springframework.stereotype.Component;

@Component
public class TestMapper {

    public TestDTO mapToDTO(TestEntity testEntity) {
        return TestDTO.builder()
                .id(testEntity.getId())
                .name(testEntity.getName())
                .lastname(testEntity.getLastname())
                .build();
    }

    public TestEntity mapToEntity(TestDTOSave testDTO) {
        return TestEntity.builder()
                .name(testDTO.getName())
                .lastname(testDTO.getLastname())
                .build();
    }
}