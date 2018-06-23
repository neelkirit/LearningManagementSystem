package com.amogh.lms.service.impl;

import com.amogh.lms.domain.Exercise;
import com.amogh.lms.domain.Template;
import com.amogh.lms.domain.enumeration.ContentType;
import com.amogh.lms.repository.ExerciseRepository;
import com.amogh.lms.repository.TemplateRepository;
import com.amogh.lms.service.ExerciseService;
import com.amogh.lms.service.ExerciseStatsService;
import com.amogh.lms.service.TemplateService;
import com.amogh.lms.service.dto.*;
import com.amogh.lms.service.mapper.ExerciseMapper;
import com.amogh.lms.service.mapper.TemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing Exercise.
 */
@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final Logger log = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    private final ExerciseRepository exerciseRepository;

    private final ExerciseMapper exerciseMapper;

    private final ExerciseStatsService exerciseStatsService;

    private final TemplateService templateService;

    public ExerciseServiceImpl(
        ExerciseRepository exerciseRepository,
        ExerciseMapper exerciseMapper,
        TemplateService templateService,
        ExerciseStatsService exerciseStatsService
    ) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
        this.templateService = templateService;
        this.exerciseStatsService = exerciseStatsService;
    }

    /**
     * Save a exercise.
     *
     * @param exerciseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExerciseDTO save(ExerciseDTO exerciseDTO) {
        log.debug("Request to save Exercise : {}", exerciseDTO);
        Exercise exercise = exerciseMapper.toEntity(exerciseDTO);
        exercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDto(exercise);
    }

    /**
     * Get all the exercises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExerciseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Exercises");
        return exerciseRepository.findAll(pageable)
            .map(exerciseMapper::toDto);
    }

    /**
     * Get one exercise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExerciseDTO findOne(Long id) {
        log.debug("Request to get Exercise : {}", id);
        Exercise exercise = exerciseRepository.findOne(id);
        return exerciseMapper.toDto(exercise);
    }

    /**
     * Delete the exercise by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exercise : {}", id);
        exerciseRepository.delete(id);
    }

    @Override
    public ExerciseDTO findByTemplateAndContentTypeAndContent(Template template, ContentType contentType, String content) {
        Exercise exercise = exerciseRepository.findByTemplateAndContentTypeAndContent(template, contentType, content);
        return exerciseMapper.toDto(exercise);
    }

    @Override
    public List<ExerciseDTO> findByTopicId(Long topicId) {
        List<Exercise> exerciseList = exerciseRepository.findByTopicId(topicId);
        return exerciseMapper.toDto(exerciseList);
    }

    /**
     * Get exercises for a given topic id
     * @param topicId the topic id
     * @return Exercise DTOs
     */
    @Override
    public List<ExerciseDetailsDTO> findExercisesByTopicId(Long topicId) {
        List<Exercise> exercisesForTopicId = this.exerciseRepository.findByTopicId(topicId);
        List<ExerciseDTO> exerciseDTOs = this.exerciseMapper.toDto(exercisesForTopicId);
        List<ExerciseDetailsDTO> exerciseDetailsDTOs = new ArrayList<>();
        for ( ExerciseDTO exerciseDTO : exerciseDTOs) {
            TemplateDTO templateDTO = this.templateService.findOne(exerciseDTO.getTemplateId());
            ExerciseDetailsDTO exerciseDetailsDTO = new ExerciseDetailsDTO();
            exerciseDetailsDTO.setupDTO(exerciseDTO, templateDTO);
            exerciseDetailsDTOs.add(exerciseDetailsDTO);
        }
        return exerciseDetailsDTOs;
    }

    @Override
    public Integer submitExerciseStats(List<ExerciseDetailsDTO> exerciseDetailsDTOS) {
        int totalCorrect = 0;
        List<ExerciseStatsDTO> exerciseStatsForUser = this.exerciseStatsService.findByLoggedInUser();
        for(ExerciseDetailsDTO exerciseDetailsDTO : exerciseDetailsDTOS) {
            Boolean foundExistingStat = false;
            if(exerciseDetailsDTO.getAnswered() == true) {
                ++totalCorrect;
            }
            for (ExerciseStatsDTO exerciseStatForUser: exerciseStatsForUser) {
                if(exerciseDetailsDTO.getId() == exerciseStatForUser.getExerciseId()) {
                    foundExistingStat = true;
                    if (exerciseStatForUser.isStatus() == false && exerciseDetailsDTO.getAnswered() != exerciseStatForUser.isStatus()) {
                        exerciseStatForUser.setStatus(exerciseDetailsDTO.getAnswered());
                        this.exerciseStatsService.save(exerciseStatForUser);
                    }
                    break;
                }
            }
            if(foundExistingStat == false) {
                ExerciseStatsDTO exerciseStatsDTO = new ExerciseStatsDTO();
                exerciseStatsDTO.setExerciseId(exerciseDetailsDTO.getId());
                exerciseStatsDTO.setUserId(exerciseDetailsDTO.getUserId());
                exerciseStatsDTO.setStatus(exerciseDetailsDTO.getAnswered());
                this.exerciseStatsService.save(exerciseStatsDTO);
            }
        }
        return totalCorrect;
    }

    /**
     * Gets the total rows in the table
     *
     * @return the total rows in the table
     */
    @Override
    public Long getTotalRows() {
        return this.exerciseRepository.count();
    }

}
