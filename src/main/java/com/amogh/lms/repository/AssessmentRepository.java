package com.amogh.lms.repository;

import com.amogh.lms.domain.Assessment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Assessment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    Assessment findByCourseId(Long courseId);

}
