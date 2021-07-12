package com.nate.upload.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("local")
@Data
public class LocalStorageProperties {
    private String fileLocation;

}
