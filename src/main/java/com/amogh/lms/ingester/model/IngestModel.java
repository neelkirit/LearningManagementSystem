package com.amogh.lms.ingester.model;

import com.amogh.lms.domain.enumeration.ContentType;
import com.amogh.lms.service.CourseService;

import java.util.List;

public class IngestModel {

    String courseName;

    String topicName;

    String content;

    ContentType contentType;

    List<String> templateNames;

    String answer;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public List<String> getTemplateNames() {
        return templateNames;
    }

    public void setTemplateNames(List<String> templateNames) {
        this.templateNames = templateNames;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
