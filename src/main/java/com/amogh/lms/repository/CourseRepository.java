package com.amogh.lms.repository;

import com.amogh.lms.domain.Course;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Finds the course by the name
     * @param name the course name
     * @return Course entity object
     */
    Course findByName(String name);
}
