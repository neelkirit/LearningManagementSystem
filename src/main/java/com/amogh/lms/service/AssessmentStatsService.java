package com.amogh.lms.service;

import com.amogh.lms.service.dto.AssessmentStatsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing AssessmentStats.
 */
public interface AssessmentStatsService {

    /**
     * Save a assessmentStats.
     *
     * @param assessmentStatsDTO the entity to save
     * @return the persisted entity
     */
    AssessmentStatsDTO save(AssessmentStatsDTO assessmentStatsDTO);

    /**
     * Get all the assessmentStats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AssessmentStatsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" assessmentStats.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AssessmentStatsDTO findOne(Long id);

    /**
     * Delete the "id" assessmentStats.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Finds the assessment for the logged in user
     * @return list of assessment DTOs
     */
    List<AssessmentStatsDTO> findAssessmentStatsByUser();
}
