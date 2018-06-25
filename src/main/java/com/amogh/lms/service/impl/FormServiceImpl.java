package com.amogh.lms.service.impl;

import com.amogh.lms.service.FormService;
import com.amogh.lms.domain.Form;
import com.amogh.lms.repository.FormRepository;
import com.amogh.lms.service.dto.FormDTO;
import com.amogh.lms.service.mapper.FormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Form.
 */
@Service
@Transactional
public class FormServiceImpl implements FormService {

    private final Logger log = LoggerFactory.getLogger(FormServiceImpl.class);

    private final FormRepository formRepository;

    private final FormMapper formMapper;

    public FormServiceImpl(FormRepository formRepository, FormMapper formMapper) {
        this.formRepository = formRepository;
        this.formMapper = formMapper;
    }

    /**
     * Save a form.
     *
     * @param formDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FormDTO save(FormDTO formDTO) {
        log.debug("Request to save Form : {}", formDTO);
        Form form = formMapper.toEntity(formDTO);
        form = formRepository.save(form);
        return formMapper.toDto(form);
    }

    /**
     * Get all the forms.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FormDTO> findAll() {
        log.debug("Request to get all Forms");
        return formRepository.findAll().stream()
            .map(formMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one form by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FormDTO findOne(Long id) {
        log.debug("Request to get Form : {}", id);
        Form form = formRepository.findOne(id);
        return formMapper.toDto(form);
    }

    /**
     * Delete the form by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Form : {}", id);
        formRepository.delete(id);
    }

    @Override
    public FormDTO findByName(String name) {
        Form form = this.formRepository.findByName(name);
        return this.formMapper.toDto(form);
    }
}
