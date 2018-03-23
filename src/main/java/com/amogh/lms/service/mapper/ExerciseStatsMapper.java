package com.amogh.lms.service.mapper;

import com.amogh.lms.domain.*;
import com.amogh.lms.service.dto.ExerciseStatsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExerciseStats and its DTO ExerciseStatsDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ExerciseMapper.class})
public interface ExerciseStatsMapper extends EntityMapper<ExerciseStatsDTO, ExerciseStats> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "exercise.id", target = "exerciseId")
    ExerciseStatsDTO toDto(ExerciseStats exerciseStats);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "exerciseId", target = "exercise")
    ExerciseStats toEntity(ExerciseStatsDTO exerciseStatsDTO);

    default ExerciseStats fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExerciseStats exerciseStats = new ExerciseStats();
        exerciseStats.setId(id);
        return exerciseStats;
    }
}
