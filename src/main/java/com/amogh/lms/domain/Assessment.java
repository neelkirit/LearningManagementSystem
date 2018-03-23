package com.amogh.lms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Assessment.
 */
@Entity
@Table(name = "assessment")
public class Assessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "threshold", nullable = false)
    private Float threshold;

    @OneToOne
    @JoinColumn(unique = true)
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getThreshold() {
        return threshold;
    }

    public Assessment threshold(Float threshold) {
        this.threshold = threshold;
        return this;
    }

    public void setThreshold(Float threshold) {
        this.threshold = threshold;
    }

    public Course getCourse() {
        return course;
    }

    public Assessment course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        Assessment assessment = (Assessment) o;
        if (assessment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assessment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assessment{" +
            "id=" + getId() +
            ", threshold=" + getThreshold() +
            "}";
    }
}
