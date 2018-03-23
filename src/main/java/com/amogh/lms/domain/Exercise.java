package com.amogh.lms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.amogh.lms.domain.enumeration.ContentType;

/**
 * A Exercise.
 */
@Entity
@Table(name = "exercise")
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "content_prefix")
    private String contentPrefix;

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private Template template;

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

    public Exercise contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public Exercise content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentPrefix() {
        return contentPrefix;
    }

    public Exercise contentPrefix(String contentPrefix) {
        this.contentPrefix = contentPrefix;
        return this;
    }

    public void setContentPrefix(String contentPrefix) {
        this.contentPrefix = contentPrefix;
    }

    public Topic getTopic() {
        return topic;
    }

    public Exercise topic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Template getTemplate() {
        return template;
    }

    public Exercise template(Template template) {
        this.template = template;
        return this;
    }

    public void setTemplate(Template template) {
        this.template = template;
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
        Exercise exercise = (Exercise) o;
        if (exercise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exercise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            ", content='" + getContent() + "'" +
            ", contentPrefix='" + getContentPrefix() + "'" +
            "}";
    }
}
