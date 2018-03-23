package com.amogh.lms.service.mapper;

import com.amogh.lms.domain.*;
import com.amogh.lms.service.dto.TopicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Topic and its DTO TopicDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface TopicMapper extends EntityMapper<TopicDTO, Topic> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    TopicDTO toDto(Topic topic);

    @Mapping(source = "courseId", target = "course")
    Topic toEntity(TopicDTO topicDTO);

    default Topic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Topic topic = new Topic();
        topic.setId(id);
        return topic;
    }
}
