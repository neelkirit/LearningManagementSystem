package com.amogh.lms.service;

import com.amogh.lms.domain.Topic;
import com.amogh.lms.service.dto.TemplateDTO;
import com.amogh.lms.service.dto.TopicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Template.
 */
public interface TemplateService {

    /**
     * Save a template.
     *
     * @param templateDTO the entity to save
     * @return the persisted entity
     */
    TemplateDTO save(TemplateDTO templateDTO);

    /**
     * Get all the templates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TemplateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" template.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TemplateDTO findOne(Long id);

    /**
     * Delete the "id" template.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Finds the topic specified by the given name
     * @param templateName the template name
     * @return TemplateDTO object just saved
     */
    TemplateDTO findByName(String templateName);
}
