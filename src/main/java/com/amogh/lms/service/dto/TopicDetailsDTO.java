package com.amogh.lms.service.dto;

public class TopicDetailsDTO extends TopicDTO {

    private Boolean isComplete;

    public void setupDTO(TopicDTO topicDTO) {
        this.setCourseId(topicDTO.getCourseId());
        this.setCourseName(topicDTO.getCourseName());
        this.setId(topicDTO.getId());
        this.setName(topicDTO.getName());
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }
}
