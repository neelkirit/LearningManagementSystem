package com.amogh.lms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FormAttributes.
 */
@Entity
@Table(name = "form_attributes")
public class FormAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private Form form;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FormAttributes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public FormAttributes type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isIsMandatory() {
        return isMandatory;
    }

    public FormAttributes isMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
        return this;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getContent() {
        return content;
    }

    public FormAttributes content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Form getForm() {
        return form;
    }

    public FormAttributes form(Form form) {
        this.form = form;
        return this;
    }

    public void setForm(Form form) {
        this.form = form;
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
        FormAttributes formAttributes = (FormAttributes) o;
        if (formAttributes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formAttributes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormAttributes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", isMandatory='" + isIsMandatory() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
