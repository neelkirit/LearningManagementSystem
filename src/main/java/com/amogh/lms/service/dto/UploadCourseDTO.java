package com.amogh.lms.service.dto;

import java.io.Serializable;

public class UploadCourseDTO implements Serializable {

    String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
