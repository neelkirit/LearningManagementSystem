package com.amogh.lms.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.amogh.lms.domain.enumeration.ContentType;

/**
 * A DTO for the Template entity.
 */
public class TemplateDTO implements Serializable {

    private Long id;

    private String style;

    @NotNull
    private ContentType contentType;

    private String contentPrefix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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
            ", style='" + getStyle() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", contentPrefix='" + getContentPrefix() + "'" +
            "}";
    }
}
