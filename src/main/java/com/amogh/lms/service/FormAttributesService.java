package com.amogh.lms.service;

import com.amogh.lms.service.dto.FormAttributesDTO;
import java.util.List;

/**
 * Service Interface for managing FormAttributes.
 */
public interface FormAttributesService {

    /**
     * Save a formAttributes.
     *
     * @param formAttributesDTO the entity to save
     * @return the persisted entity
     */
    FormAttributesDTO save(FormAttributesDTO formAttributesDTO);

    /**
     * Get all the formAttributes.
     *
     * @return the list of entities
     */
    List<FormAttributesDTO> findAll();

    /**
     * Get the "id" formAttributes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FormAttributesDTO findOne(Long id);

    /**
     * Delete the "id" formAttributes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
