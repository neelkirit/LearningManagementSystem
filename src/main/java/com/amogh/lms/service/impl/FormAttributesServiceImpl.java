package com.amogh.lms.service.impl;

import com.amogh.lms.service.FormAttributesService;
import com.amogh.lms.domain.FormAttributes;
import com.amogh.lms.repository.FormAttributesRepository;
import com.amogh.lms.service.dto.FormAttributesDTO;
import com.amogh.lms.service.mapper.FormAttributesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FormAttributes.
 */
@Service
@Transactional
public class FormAttributesServiceImpl implements FormAttributesService {

    private final Logger log = LoggerFactory.getLogger(FormAttributesServiceImpl.class);

    private final FormAttributesRepository formAttributesRepository;

    private final FormAttributesMapper formAttributesMapper;

    public FormAttributesServiceImpl(FormAttributesRepository formAttributesRepository, FormAttributesMapper formAttributesMapper) {
        this.formAttributesRepository = formAttributesRepository;
        this.formAttributesMapper = formAttributesMapper;
    }

    /**
     * Save a formAttributes.
     *
     * @param formAttributesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FormAttributesDTO save(FormAttributesDTO formAttributesDTO) {
        log.debug("Request to save FormAttributes : {}", formAttributesDTO);
        FormAttributes formAttributes = formAttributesMapper.toEntity(formAttributesDTO);
        formAttributes = formAttributesRepository.save(formAttributes);
        return formAttributesMapper.toDto(formAttributes);
    }

    /**
     * Get all the formAttributes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FormAttributesDTO> findAll() {
        log.debug("Request to get all FormAttributes");
        return formAttributesRepository.findAll().stream()
            .map(formAttributesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one formAttributes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FormAttributesDTO findOne(Long id) {
        log.debug("Request to get FormAttributes : {}", id);
        FormAttributes formAttributes = formAttributesRepository.findOne(id);
        return formAttributesMapper.toDto(formAttributes);
    }

    /**
     * Delete the formAttributes by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormAttributes : {}", id);
        formAttributesRepository.delete(id);
    }
}
