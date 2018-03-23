package com.amogh.lms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AssessmentStats entity.
 */
public class AssessmentStatsDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Float score;

    private Long userId;

    private String userLogin;

    private Long assessmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssessmentStatsDTO assessmentStatsDTO = (AssessmentStatsDTO) o;
        if(assessmentStatsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assessmentStatsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssessmentStatsDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            "}";
    }
}
