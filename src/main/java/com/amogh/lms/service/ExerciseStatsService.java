package com.amogh.lms.service;

import com.amogh.lms.service.dto.ExerciseStatsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ExerciseStats.
 */
public interface ExerciseStatsService {

    /**
     * Save a exerciseStats.
     *
     * @param exerciseStatsDTO the entity to save
     * @return the persisted entity
     */
    ExerciseStatsDTO save(ExerciseStatsDTO exerciseStatsDTO);

    /**
     * Get all the exerciseStats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExerciseStatsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" exerciseStats.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExerciseStatsDTO findOne(Long id);

    /**
     * Delete the "id" exerciseStats.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    /**
     * Finds all the questions attempted by the user
     * @return list of exercise stats entires
     */
    List<ExerciseStatsDTO> findByLoggedInUser();
}
