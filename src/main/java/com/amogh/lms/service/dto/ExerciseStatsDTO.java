package com.amogh.lms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ExerciseStats entity.
 */
public class ExerciseStatsDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean status;

    private Long userId;

    private String userLogin;

    private Long exerciseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExerciseStatsDTO exerciseStatsDTO = (ExerciseStatsDTO) o;
        if(exerciseStatsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exerciseStatsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExerciseStatsDTO{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
