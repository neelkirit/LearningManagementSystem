package com.amogh.lms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AssessmentStats.
 */
@Entity
@Table(name = "assessment_stats")
public class AssessmentStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "score", nullable = false)
    private Float score;

    @ManyToOne
    private User user;

    @ManyToOne
    private Assessment assessment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getScore() {
        return score;
    }

    public AssessmentStats score(Float score) {
        this.score = score;
        return this;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public AssessmentStats user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public AssessmentStats assessment(Assessment assessment) {
        this.assessment = assessment;
        return this;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssessmentStats assessmentStats = (AssessmentStats) o;
        if (assessmentStats.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assessmentStats.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssessmentStats{" +
            "id=" + getId() +
            ", score=" + getScore() +
            "}";
    }
}
