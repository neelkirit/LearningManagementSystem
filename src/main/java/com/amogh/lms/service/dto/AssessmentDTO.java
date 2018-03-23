package com.amogh.lms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Assessment entity.
 */
public class AssessmentDTO implements Serializable {

    private Long id;

    @NotNull
    private Float threshold;

    private Long courseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getThreshold() {
        return threshold;
    }

    public void setThreshold(Float threshold) {
        this.threshold = threshold;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssessmentDTO assessmentDTO = (AssessmentDTO) o;
        if(assessmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assessmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssessmentDTO{" +
            "id=" + getId() +
            ", threshold=" + getThreshold() +
            "}";
    }
}
