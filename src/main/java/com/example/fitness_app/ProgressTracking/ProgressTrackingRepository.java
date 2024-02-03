package com.example.fitness_app.ProgressTracking;

import com.example.fitness_app.ExerciseSession.ExerciseSessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressTrackingRepository extends JpaRepository<ProgressTrackingEntity, String> {

    @Query("SELECT e FROM ProgressTrackingEntity e WHERE e.deletedAt IS NULL")
    Page<ProgressTrackingEntity> findAllByDeletedAtIsNull(Pageable pageable);


    @Query("SELECT pt FROM ProgressTrackingEntity pt " +
            "JOIN pt.exerciseSession e " +
            "JOIN e.user u " +
            "WHERE e.id = :exerciseId AND u.id = :userId")
    List<ProgressTrackingEntity> findAllByExerciseIdAndUserId(@Param("exerciseId") String exerciseId, @Param("userId") String userId);


}
