package com.amogh.lms.service.mapper;

import com.amogh.lms.domain.*;
import com.amogh.lms.service.dto.ExerciseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Exercise and its DTO ExerciseDTO.
 */
@Mapper(componentModel = "spring", uses = {TopicMapper.class, TemplateMapper.class})
public interface ExerciseMapper extends EntityMapper<ExerciseDTO, Exercise> {

    @Mapping(source = "topic.id", target = "topicId")
    @Mapping(source = "topic.name", target = "topicName")
    @Mapping(source = "template.id", target = "templateId")
    ExerciseDTO toDto(Exercise exercise);

    @Mapping(source = "topicId", target = "topic")
    @Mapping(source = "templateId", target = "template")
    Exercise toEntity(ExerciseDTO exerciseDTO);

    default Exercise fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exercise exercise = new Exercise();
        exercise.setId(id);
        return exercise;
    }
}
