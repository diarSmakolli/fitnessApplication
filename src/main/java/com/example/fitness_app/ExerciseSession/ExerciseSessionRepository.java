package com.example.fitness_app.ExerciseSession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseSessionRepository extends JpaRepository<ExerciseSessionEntity, String> {
}
