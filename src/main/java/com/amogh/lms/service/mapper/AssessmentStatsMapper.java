package com.amogh.lms.service.mapper;

import com.amogh.lms.domain.*;
import com.amogh.lms.service.dto.AssessmentStatsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AssessmentStats and its DTO AssessmentStatsDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AssessmentMapper.class})
public interface AssessmentStatsMapper extends EntityMapper<AssessmentStatsDTO, AssessmentStats> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "assessment.id", target = "assessmentId")
    AssessmentStatsDTO toDto(AssessmentStats assessmentStats);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "assessmentId", target = "assessment")
    AssessmentStats toEntity(AssessmentStatsDTO assessmentStatsDTO);

    default AssessmentStats fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssessmentStats assessmentStats = new AssessmentStats();
        assessmentStats.setId(id);
        return assessmentStats;
    }
}
