package com.example.fitness_app.ProgressTracking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressTrackingRepository extends JpaRepository<ProgressTrackingEntity, String> {

    ProgressTrackingEntity findByExerciseSessionIdAndCreatedBy(String exerciseSessionId, String createdBy);

    List<ProgressTrackingEntity> findAllByExerciseSessionId(String exerciseSessionId);

}
