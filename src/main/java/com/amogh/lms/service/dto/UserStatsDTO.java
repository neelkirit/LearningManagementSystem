package com.amogh.lms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Created by Anvitha G K Bhat on 8/5/18.
 */

@Data
@Builder
@AllArgsConstructor
public class UserStatsDTO
{
    Integer progressPercent;
    Integer questionsAttempted;
    Integer wrongAnswers;
    Map<String, CourseStatDTO> courseStats;
}
