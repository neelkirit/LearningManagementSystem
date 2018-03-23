package com.amogh.lms.repository;

import com.amogh.lms.domain.ExerciseStats;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ExerciseStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExerciseStatsRepository extends JpaRepository<ExerciseStats, Long> {

    @Query("select exercise_stats from ExerciseStats exercise_stats where exercise_stats.user.login = ?#{principal.username}")
    List<ExerciseStats> findByUserIsCurrentUser();

}
