package com.amogh.lms.service;

import com.amogh.lms.service.dto.FormDTO;
import java.util.List;

/**
 * Service Interface for managing Form.
 */
public interface FormService {

    /**
     * Save a form.
     *
     * @param formDTO the entity to save
     * @return the persisted entity
     */
    FormDTO save(FormDTO formDTO);

    /**
     * Get all the forms.
     *
     * @return the list of entities
     */
    List<FormDTO> findAll();

    /**
     * Get the "id" form.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FormDTO findOne(Long id);

    /**
     * Delete the "id" form.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * The form DTO for the given name
     * @param name the form name
     * @return form DTO object
     */
    FormDTO findByName(String name);
}
