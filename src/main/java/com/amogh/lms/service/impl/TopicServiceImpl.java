package com.amogh.lms.service.impl;

import com.amogh.lms.domain.Exercise;
import com.amogh.lms.service.TopicService;
import com.amogh.lms.domain.Topic;
import com.amogh.lms.repository.TopicRepository;
import com.amogh.lms.service.dto.TopicDTO;
import com.amogh.lms.service.mapper.TopicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Topic.
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    /**
     * Save a topic.
     *
     * @param topicDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TopicDTO save(TopicDTO topicDTO) {
        log.debug("Request to save Topic : {}", topicDTO);
        Topic topic = topicMapper.toEntity(topicDTO);
        topic = topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    /**
     * Get all the topics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TopicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Topics");
        return topicRepository.findAll(pageable)
            .map(topicMapper::toDto);
    }

    /**
     * Get one topic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TopicDTO findOne(Long id) {
        log.debug("Request to get Topic : {}", id);
        Topic topic = topicRepository.findOne(id);
        return topicMapper.toDto(topic);
    }

    /**
     * Delete the topic by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Topic : {}", id);
        topicRepository.delete(id);
    }

    /**
     * Finds the topic by Name
     * @param topicName the topic name
     * @return TopicDTO object if found
     */
    @Override
    public TopicDTO findByName(String topicName) {
        Topic topic = topicRepository.findByName(topicName);
        return topicMapper.toDto(topic);
    }

    /**
     * Get all the topics.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Topic> getAll()
    {
        return topicRepository.findAll();
    }
}
