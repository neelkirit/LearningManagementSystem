package com.amogh.lms.repository;

import com.amogh.lms.domain.Exercise;
import com.amogh.lms.domain.Template;
import com.amogh.lms.domain.enumeration.ContentType;
import java.util.*;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Exercise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Exercise findByTemplateAndContentTypeAndContent(Template template, ContentType contentType, String content);

    List<Exercise> findByTopicId(Long topicId);
}
