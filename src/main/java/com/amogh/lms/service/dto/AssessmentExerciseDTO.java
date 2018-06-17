package com.amogh.lms.service.dto;

public class AssessmentExerciseDTO extends ExerciseDetailsDTO {

    AssessmentDTO assessmentDTO;

    public void setupDTO(ExerciseDTO exerciseDTO, TemplateDTO templateDTO, AssessmentDTO assessmentDTO) {
        super.setupDTO(exerciseDTO, templateDTO);
        this.setAssessmentDTO(assessmentDTO);
    }

    public AssessmentDTO getAssessmentDTO() {
        return assessmentDTO;
    }

    public void setAssessmentDTO(AssessmentDTO assessmentDTO) {
        this.assessmentDTO = assessmentDTO;
    }
}
