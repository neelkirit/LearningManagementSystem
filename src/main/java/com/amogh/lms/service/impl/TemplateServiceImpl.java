package com.amogh.lms.service.impl;

import com.amogh.lms.service.TemplateService;
import com.amogh.lms.domain.Template;
import com.amogh.lms.repository.TemplateRepository;
import com.amogh.lms.service.dto.ExerciseDTO;
import com.amogh.lms.service.dto.TemplateDTO;
import com.amogh.lms.service.dto.TopicDTO;
import com.amogh.lms.service.mapper.TemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Service Implementation for managing Template.
 */
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private final Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);

    private final TemplateRepository templateRepository;

    private final TemplateMapper templateMapper;

    public TemplateServiceImpl(TemplateRepository templateRepository, TemplateMapper templateMapper) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    /**
     * Save a template.
     *
     * @param templateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TemplateDTO save(TemplateDTO templateDTO) {
        log.debug("Request to save Template : {}", templateDTO);
        Template template = templateMapper.toEntity(templateDTO);
        template = templateRepository.save(template);
        return templateMapper.toDto(template);
    }

    /**
     * Get all the templates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Templates");
        return templateRepository.findAll(pageable)
            .map(templateMapper::toDto);
    }

    /**
     * Get one template by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TemplateDTO findOne(Long id) {
        log.debug("Request to get Template : {}", id);
        Template template = templateRepository.findOne(id);
        return templateMapper.toDto(template);
    }

    /**
     * Delete the template by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Template : {}", id);
        templateRepository.delete(id);
    }

    @Override
    public TemplateDTO findByName(String templateName) {
        Template template = templateRepository.findByName(templateName);
        return this.templateMapper.toDto(template);
    }

    @Override
    public Set<TemplateDTO> findByExerciseDTO(List<ExerciseDTO> exerciseDTOList) {
        Set<TemplateDTO> templateDTOList = new HashSet();
        for(ExerciseDTO exerciseDTO : exerciseDTOList){
            templateDTOList.add(this.findOne(exerciseDTO.getTemplateId()));
        }
        return templateDTOList;
    }
}
