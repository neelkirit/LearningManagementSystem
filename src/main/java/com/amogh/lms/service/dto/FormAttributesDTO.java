package com.amogh.lms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FormAttributes entity.
 */
public class FormAttributesDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private Boolean isMandatory;

    private String content;

    private Long formId;

    private String formName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormAttributesDTO formAttributesDTO = (FormAttributesDTO) o;
        if(formAttributesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formAttributesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormAttributesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", isMandatory='" + isIsMandatory() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
