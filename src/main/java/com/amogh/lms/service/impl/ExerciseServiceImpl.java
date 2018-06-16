package com.amogh.lms.service.impl;

import com.amogh.lms.domain.Template;
import com.amogh.lms.domain.enumeration.ContentType;
import com.amogh.lms.service.ExerciseService;
import com.amogh.lms.domain.Exercise;
import com.amogh.lms.repository.ExerciseRepository;
import com.amogh.lms.service.dto.ExerciseDTO;
import com.amogh.lms.service.dto.TopicDTO;
import com.amogh.lms.service.mapper.ExerciseMapper;
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

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
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

    @Override
    public List<ExerciseDTO> findByTopicDTOList(List<TopicDTO> topicDTOList) {
        List<ExerciseDTO> exerciseDTOList = new ArrayList();
        for (TopicDTO topicDTO : topicDTOList){
            exerciseDTOList.addAll(this.findByTopicId(topicDTO.getId()));
        }
        return exerciseDTOList;
    }

    /**
     * Get exercises for a given topic id
     * @param topicId the topic id
     * @return Exercise DTOs
     */
    @Override
    public List<ExerciseDTO> findExercisesByTopicId(Long topicId) {
        List<Exercise> exercisesForTopicId = this.exerciseRepository.findByTopicId(topicId);
        return this.exerciseMapper.toDto(exercisesForTopicId);
    }

}
