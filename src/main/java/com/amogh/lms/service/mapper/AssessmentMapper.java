package com.amogh.lms.service.mapper;

import com.amogh.lms.domain.*;
import com.amogh.lms.service.dto.AssessmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Assessment and its DTO AssessmentDTO.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface AssessmentMapper extends EntityMapper<AssessmentDTO, Assessment> {

    @Mapping(source = "course.id", target = "courseId")
    AssessmentDTO toDto(Assessment assessment);

    @Mapping(source = "courseId", target = "course")
    Assessment toEntity(AssessmentDTO assessmentDTO);

    default Assessment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Assessment assessment = new Assessment();
        assessment.setId(id);
        return assessment;
    }
}
