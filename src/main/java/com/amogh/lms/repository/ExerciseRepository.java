package com.amogh.lms.repository;

import com.amogh.lms.domain.Exercise;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Exercise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}
