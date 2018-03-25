package com.amogh.lms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.amogh.lms.domain.enumeration.ContentType;

import com.amogh.lms.domain.enumeration.ContentStyle;

/**
 * A Template.
 */
@Entity
@Table(name = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(name = "content_prefix")
    private String contentPrefix;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_style")
    private ContentStyle contentStyle;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public Template contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContentPrefix() {
        return contentPrefix;
    }

    public Template contentPrefix(String contentPrefix) {
        this.contentPrefix = contentPrefix;
        return this;
    }

    public void setContentPrefix(String contentPrefix) {
        this.contentPrefix = contentPrefix;
    }

    public ContentStyle getContentStyle() {
        return contentStyle;
    }

    public Template contentStyle(ContentStyle contentStyle) {
        this.contentStyle = contentStyle;
        return this;
    }

    public void setContentStyle(ContentStyle contentStyle) {
        this.contentStyle = contentStyle;
    }

    public String getName() {
        return name;
    }

    public Template name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        Template template = (Template) o;
        if (template.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), template.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            ", contentPrefix='" + getContentPrefix() + "'" +
            ", contentStyle='" + getContentStyle() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
