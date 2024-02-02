package com.example.fitness_app.ProgressTracking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressTrackingRepository extends JpaRepository<ProgressTrackingEntity, String> {
}
