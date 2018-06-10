package com.amogh.lms.service.dto;

import java.util.*;

public class AssessmentExerciseDTO {

    private AssessmentDTO assessmentDTO;
    private List<ExerciseDTO> exerciseDTOList;
    private Set<TemplateDTO> templateDTOList;

    public Set<TemplateDTO> getTemplateDTOList() {
        return templateDTOList;
    }



    public AssessmentDTO getAssessmentDTO() {
        return assessmentDTO;
    }

    public void setAssessmentDTO(AssessmentDTO assessmentDTO) {
        this.assessmentDTO = assessmentDTO;
    }

    public List<ExerciseDTO> getExerciseDTOList() {
        return exerciseDTOList;
    }

    public void setExerciseDTOList(List<ExerciseDTO> exerciseDTOList) {
        this.exerciseDTOList = exerciseDTOList;
    }

    public void setTemplateDTOList(Set<TemplateDTO> templateDTOList) { this.templateDTOList = templateDTOList;}
}
