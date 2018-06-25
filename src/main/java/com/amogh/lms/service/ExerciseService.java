package com.amogh.lms.service;

import com.amogh.lms.domain.Template;
import com.amogh.lms.domain.enumeration.ContentType;
import com.amogh.lms.service.dto.ExerciseDTO;
import com.amogh.lms.service.dto.ExerciseDetailsDTO;
import com.amogh.lms.service.dto.TopicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Exercise.
 */
public interface ExerciseService {

    /**
     * Save a exercise.
     *
     * @param exerciseDTO the entity to save
     * @return the persisted entity
     */
    ExerciseDTO save(ExerciseDTO exerciseDTO);

    /**
     * Get all the exercises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExerciseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" exercise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExerciseDTO findOne(Long id);

    /**
     * Delete the "id" exercise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Finds the exercise object based on template id, content type and content
     * @param template the template object
     * @param contentType the content type
     * @param content the content
     * @return ExceriseDTO object
     */
    ExerciseDTO findByTemplateAndContentTypeAndContentAndAnswer(Template template, ContentType contentType, String content, String answer);

    /**
     * Finds the excercise given a topic ID
     * @param topicId the topic ID
     * @return ExerciseDTO object
     */
    List<ExerciseDTO> findByTopicId(Long topicId);

    /**
     * Get exercises for a given topic id
     * @param topicId the topic id
     * @return Exercise details DTOs
     */
    List<ExerciseDetailsDTO> findExercisesByTopicId(Long topicId);

    /**
     * Submits the exercise details/stats for a user to be stored
     * @param exerciseDetailsDTOS the exercise details
     * @return number of exercise stats updated
     */
    Integer submitExerciseStats(List<ExerciseDetailsDTO> exerciseDetailsDTOS);

    /**
     * Gets the total rows in the table
     * @return the total rows in the table
     */
    Long getTotalRows();
}
