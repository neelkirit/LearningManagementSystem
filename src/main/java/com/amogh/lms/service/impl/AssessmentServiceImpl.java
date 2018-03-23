package com.amogh.lms.service.impl;

import com.amogh.lms.service.AssessmentService;
import com.amogh.lms.domain.Assessment;
import com.amogh.lms.repository.AssessmentRepository;
import com.amogh.lms.service.dto.AssessmentDTO;
import com.amogh.lms.service.mapper.AssessmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Assessment.
 */
@Service
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    private final Logger log = LoggerFactory.getLogger(AssessmentServiceImpl.class);

    private final AssessmentRepository assessmentRepository;

    private final AssessmentMapper assessmentMapper;

    public AssessmentServiceImpl(AssessmentRepository assessmentRepository, AssessmentMapper assessmentMapper) {
        this.assessmentRepository = assessmentRepository;
        this.assessmentMapper = assessmentMapper;
    }

    /**
     * Save a assessment.
     *
     * @param assessmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AssessmentDTO save(AssessmentDTO assessmentDTO) {
        log.debug("Request to save Assessment : {}", assessmentDTO);
        Assessment assessment = assessmentMapper.toEntity(assessmentDTO);
        assessment = assessmentRepository.save(assessment);
        return assessmentMapper.toDto(assessment);
    }

    /**
     * Get all the assessments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assessments");
        return assessmentRepository.findAll(pageable)
            .map(assessmentMapper::toDto);
    }

    /**
     * Get one assessment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AssessmentDTO findOne(Long id) {
        log.debug("Request to get Assessment : {}", id);
        Assessment assessment = assessmentRepository.findOne(id);
        return assessmentMapper.toDto(assessment);
    }

    /**
     * Delete the assessment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assessment : {}", id);
        assessmentRepository.delete(id);
    }
}
