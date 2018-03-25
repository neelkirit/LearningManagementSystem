package com.amogh.lms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.amogh.lms.domain.enumeration.ContentType;
import com.amogh.lms.domain.enumeration.ContentStyle;

/**
 * A DTO for the Template entity.
 */
public class TemplateDTO implements Serializable {

    private Long id;

    @NotNull
    private ContentType contentType;

    private String contentPrefix;

    private ContentStyle contentStyle;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContentPrefix() {
        return contentPrefix;
    }

    public void setContentPrefix(String contentPrefix) {
        this.contentPrefix = contentPrefix;
    }

    public ContentStyle getContentStyle() {
        return contentStyle;
    }

    public void setContentStyle(ContentStyle contentStyle) {
        this.contentStyle = contentStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TemplateDTO templateDTO = (TemplateDTO) o;
        if(templateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), templateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TemplateDTO{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            ", contentPrefix='" + getContentPrefix() + "'" +
            ", contentStyle='" + getContentStyle() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
