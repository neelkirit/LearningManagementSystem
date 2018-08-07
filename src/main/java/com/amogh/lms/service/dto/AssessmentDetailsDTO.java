package com.amogh.lms.service.dto;

public class AssessmentDetailsDTO extends AssessmentDTO {

    private Boolean isComplete;

    public void setupDTO(AssessmentDTO assessmentDTO) {
        this.setCourseId(assessmentDTO.getCourseId());
        this.setThreshold(assessmentDTO.getThreshold());
        this.setId(assessmentDTO.getId());
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }
}
