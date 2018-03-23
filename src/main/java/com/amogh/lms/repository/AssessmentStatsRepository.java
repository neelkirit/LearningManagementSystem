package com.amogh.lms.repository;

import com.amogh.lms.domain.AssessmentStats;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the AssessmentStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssessmentStatsRepository extends JpaRepository<AssessmentStats, Long> {

    @Query("select assessment_stats from AssessmentStats assessment_stats where assessment_stats.user.login = ?#{principal.username}")
    List<AssessmentStats> findByUserIsCurrentUser();

}
