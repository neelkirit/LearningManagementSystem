package com.amogh.lms.service;

import com.amogh.lms.service.dto.AadharEnrollFormDTO;
import java.util.List;

/**
 * Service Interface for managing AadharEnrollForm.
 */
public interface AadharEnrollFormService {

    /**
     * Save a aadharEnrollForm.
     *
     * @param aadharEnrollFormDTO the entity to save
     * @return the persisted entity
     */
    AadharEnrollFormDTO save(AadharEnrollFormDTO aadharEnrollFormDTO);

    /**
     * Get all the aadharEnrollForms.
     *
     * @return the list of entities
     */
    List<AadharEnrollFormDTO> findAll();

    /**
     * Get the "id" aadharEnrollForm.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AadharEnrollFormDTO findOne(Long id);

    /**
     * Delete the "id" aadharEnrollForm.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
