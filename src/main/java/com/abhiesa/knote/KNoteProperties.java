package com.abhiesa.knote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author abhiesa
 * */
@ConfigurationProperties(prefix = "knote")
class KNoteProperties {

    @Value("${uploadDir:/tmp/uploads/}")
    private String uploadDir;

    public KNoteProperties() {
        this(null);
    }

    public KNoteProperties(final String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }
}