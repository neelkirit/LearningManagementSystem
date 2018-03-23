package com.amogh.lms.service.impl;

import com.amogh.lms.service.AssessmentStatsService;
import com.amogh.lms.domain.AssessmentStats;
import com.amogh.lms.repository.AssessmentStatsRepository;
import com.amogh.lms.service.dto.AssessmentStatsDTO;
import com.amogh.lms.service.mapper.AssessmentStatsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AssessmentStats.
 */
@Service
@Transactional
public class AssessmentStatsServiceImpl implements AssessmentStatsService {

    private final Logger log = LoggerFactory.getLogger(AssessmentStatsServiceImpl.class);

    private final AssessmentStatsRepository assessmentStatsRepository;

    private final AssessmentStatsMapper assessmentStatsMapper;

    public AssessmentStatsServiceImpl(AssessmentStatsRepository assessmentStatsRepository, AssessmentStatsMapper assessmentStatsMapper) {
        this.assessmentStatsRepository = assessmentStatsRepository;
        this.assessmentStatsMapper = assessmentStatsMapper;
    }

    /**
     * Save a assessmentStats.
     *
     * @param assessmentStatsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AssessmentStatsDTO save(AssessmentStatsDTO assessmentStatsDTO) {
        log.debug("Request to save AssessmentStats : {}", assessmentStatsDTO);
        AssessmentStats assessmentStats = assessmentStatsMapper.toEntity(assessmentStatsDTO);
        assessmentStats = assessmentStatsRepository.save(assessmentStats);
        return assessmentStatsMapper.toDto(assessmentStats);
    }

    /**
     * Get all the assessmentStats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssessmentStatsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssessmentStats");
        return assessmentStatsRepository.findAll(pageable)
            .map(assessmentStatsMapper::toDto);
    }

    /**
     * Get one assessmentStats by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AssessmentStatsDTO findOne(Long id) {
        log.debug("Request to get AssessmentStats : {}", id);
        AssessmentStats assessmentStats = assessmentStatsRepository.findOne(id);
        return assessmentStatsMapper.toDto(assessmentStats);
    }

    /**
     * Delete the assessmentStats by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AssessmentStats : {}", id);
        assessmentStatsRepository.delete(id);
    }
}
