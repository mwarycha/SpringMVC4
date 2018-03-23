package com.twitter.TwitterEduApp.configurations;

/**
 * Created by EMAWARY on 2018-02-05.
 */
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "upload.pictures")
public class PictureUploadProperties {

    private Resource uploadPath;
    private Resource anonymousPicture;

    public Resource getAnonymousPicture() {
        return anonymousPicture;
    }
    public void setAnonymousPicture(String anonymousPicture) {
        this.anonymousPicture = new DefaultResourceLoader().getResource(anonymousPicture);
    }
    public Resource getUploadPath() {
        return uploadPath;
    }
    public void setUploadPath(String uploadPath) {
        this.uploadPath = new DefaultResourceLoader().getResource(uploadPath);
    }
}