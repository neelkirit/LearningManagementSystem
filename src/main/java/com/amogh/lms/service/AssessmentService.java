package com.amogh.lms.service;

import com.amogh.lms.service.dto.AssessmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Assessment.
 */
public interface AssessmentService {

    /**
     * Save a assessment.
     *
     * @param assessmentDTO the entity to save
     * @return the persisted entity
     */
    AssessmentDTO save(AssessmentDTO assessmentDTO);

    /**
     * Get all the assessments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AssessmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" assessment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AssessmentDTO findOne(Long id);

    /**
     * Delete the "id" assessment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
