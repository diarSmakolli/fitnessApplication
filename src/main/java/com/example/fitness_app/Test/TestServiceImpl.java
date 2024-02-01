package com.example.fitness_app.Test;

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
public class TestServiceImpl implements TestService {
    private static final Logger logger = Logger.getLogger(TestServiceImpl.class.getName());

    private final TestRepository testRepository;
    private final TestMapper testMapper;

    public TestServiceImpl(TestRepository testRepository, TestMapper testMapper) {
        this.testRepository = testRepository;
        this.testMapper = testMapper;
    }


    @Override
    @Transactional
    public TestDTO saveTest(TestDTOSave testDTO) {
            TestEntity testEntity = testMapper.mapToEntity(testDTO);
            TestEntity savedTest = testRepository.save(testEntity);
            return testMapper.mapToDTO(savedTest);
    }

    @Override
    public TestEntity findTestById(String id) {
        return testRepository.findById(id).get();
    }





}
