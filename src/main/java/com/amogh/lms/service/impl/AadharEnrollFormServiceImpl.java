package com.amogh.lms.service.impl;

import com.amogh.lms.service.AadharEnrollFormService;
import com.amogh.lms.domain.AadharEnrollForm;
import com.amogh.lms.repository.AadharEnrollFormRepository;
import com.amogh.lms.service.dto.AadharEnrollFormDTO;
import com.amogh.lms.service.mapper.AadharEnrollFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AadharEnrollForm.
 */
@Service
@Transactional
public class AadharEnrollFormServiceImpl implements AadharEnrollFormService {

    private final Logger log = LoggerFactory.getLogger(AadharEnrollFormServiceImpl.class);

    private final AadharEnrollFormRepository aadharEnrollFormRepository;

    private final AadharEnrollFormMapper aadharEnrollFormMapper;

    public AadharEnrollFormServiceImpl(AadharEnrollFormRepository aadharEnrollFormRepository, AadharEnrollFormMapper aadharEnrollFormMapper) {
        this.aadharEnrollFormRepository = aadharEnrollFormRepository;
        this.aadharEnrollFormMapper = aadharEnrollFormMapper;
    }

    /**
     * Save a aadharEnrollForm.
     *
     * @param aadharEnrollFormDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AadharEnrollFormDTO save(AadharEnrollFormDTO aadharEnrollFormDTO) {
        log.debug("Request to save AadharEnrollForm : {}", aadharEnrollFormDTO);
        AadharEnrollForm aadharEnrollForm = aadharEnrollFormMapper.toEntity(aadharEnrollFormDTO);
        aadharEnrollForm = aadharEnrollFormRepository.save(aadharEnrollForm);
        return aadharEnrollFormMapper.toDto(aadharEnrollForm);
    }

    /**
     * Get all the aadharEnrollForms.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AadharEnrollFormDTO> findAll() {
        log.debug("Request to get all AadharEnrollForms");
        return aadharEnrollFormRepository.findAll().stream()
            .map(aadharEnrollFormMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one aadharEnrollForm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AadharEnrollFormDTO findOne(Long id) {
        log.debug("Request to get AadharEnrollForm : {}", id);
        AadharEnrollForm aadharEnrollForm = aadharEnrollFormRepository.findOne(id);
        return aadharEnrollFormMapper.toDto(aadharEnrollForm);
    }

    /**
     * Delete the aadharEnrollForm by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AadharEnrollForm : {}", id);
        aadharEnrollFormRepository.delete(id);
    }
}
