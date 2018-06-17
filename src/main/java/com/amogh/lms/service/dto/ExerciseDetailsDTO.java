package com.amogh.lms.service.dto;


import com.amogh.lms.domain.enumeration.ContentType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Exercise Details entity.
 */
public class ExerciseDetailsDTO extends ExerciseDTO {

    private TemplateDTO template;


    public TemplateDTO getTemplate() {
        return this.template;
    }

    public void setTemplate(TemplateDTO template) { this.template = template; }

    public void setupDTO(ExerciseDTO exerciseDTO, TemplateDTO templateDTO) {
        this.setAnswer(exerciseDTO.getAnswer());
        this.setContent(exerciseDTO.getContent());
        this.setContentType(exerciseDTO.getContentType());
        this.setId(exerciseDTO.getId());
        this.setTemplate(templateDTO);
        this.setTopicId(exerciseDTO.getTopicId());
        this.setTopicName(exerciseDTO.getTopicName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExerciseDetailsDTO exerciseDTO = (ExerciseDetailsDTO) o;
        if(exerciseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exerciseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExerciseDetailsDTO{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            ", content='" + getContent() + "'" +
            ", answer='" + getAnswer() + "'" +
            "}";
    }
}
