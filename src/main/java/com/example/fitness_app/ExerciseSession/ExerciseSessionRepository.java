package com.example.fitness_app.ExerciseSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseSessionRepository extends JpaRepository<ExerciseSessionEntity, String> {

    @Query("SELECT e FROM ExerciseSessionEntity e WHERE e.deletedAt IS NULL")
    Page<ExerciseSessionEntity> findAllByDeletedAtIsNull(Pageable pageable);

    @Query("SELECT e FROM ExerciseSessionEntity e WHERE e.deletedAt IS NULL AND e.user.id = :userId")
    List<ExerciseSessionEntity> findAllByUserIdAndDeletedAtIsNull(@Param("userId") String userId);


    @Query("SELECT e FROM ExerciseSessionEntity e WHERE e.id = :exerciseId AND e.user.id = :userId AND e.deletedAt IS NULL")
    ExerciseSessionEntity findExerciseByIdAndUserIdAndDeletedAtIsNull(@Param("exerciseId") String exerciseId, @Param("userId") String userId);





}
