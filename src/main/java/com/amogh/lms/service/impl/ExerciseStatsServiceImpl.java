package com.amogh.lms.service.impl;

import com.amogh.lms.service.ExerciseStatsService;
import com.amogh.lms.domain.ExerciseStats;
import com.amogh.lms.repository.ExerciseStatsRepository;
import com.amogh.lms.service.dto.ExerciseStatsDTO;
import com.amogh.lms.service.mapper.ExerciseStatsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing ExerciseStats.
 */
@Service
@Transactional
public class ExerciseStatsServiceImpl implements ExerciseStatsService {

    private final Logger log = LoggerFactory.getLogger(ExerciseStatsServiceImpl.class);

    private final ExerciseStatsRepository exerciseStatsRepository;

    private final ExerciseStatsMapper exerciseStatsMapper;

    public ExerciseStatsServiceImpl(ExerciseStatsRepository exerciseStatsRepository, ExerciseStatsMapper exerciseStatsMapper) {
        this.exerciseStatsRepository = exerciseStatsRepository;
        this.exerciseStatsMapper = exerciseStatsMapper;
    }

    /**
     * Save a exerciseStats.
     *
     * @param exerciseStatsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExerciseStatsDTO save(ExerciseStatsDTO exerciseStatsDTO) {
        log.debug("Request to save ExerciseStats : {}", exerciseStatsDTO);
        ExerciseStats exerciseStats = exerciseStatsMapper.toEntity(exerciseStatsDTO);
        exerciseStats = exerciseStatsRepository.save(exerciseStats);
        return exerciseStatsMapper.toDto(exerciseStats);
    }

    /**
     * Get all the exerciseStats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExerciseStatsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseStats");
        return exerciseStatsRepository.findAll(pageable)
            .map(exerciseStatsMapper::toDto);
    }

    /**
     * Get one exerciseStats by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExerciseStatsDTO findOne(Long id) {
        log.debug("Request to get ExerciseStats : {}", id);
        ExerciseStats exerciseStats = exerciseStatsRepository.findOne(id);
        return exerciseStatsMapper.toDto(exerciseStats);
    }

    /**
     * Delete the exerciseStats by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExerciseStats : {}", id);
        exerciseStatsRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseStats> getUserStats()
    {
        return exerciseStatsRepository.findByUserIsCurrentUser();
    }
}
