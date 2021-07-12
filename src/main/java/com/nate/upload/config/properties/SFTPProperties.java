package com.nate.upload.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sftp")
@Data
public class SFTPProperties {
    private String remoteHost;
    private String userName;
    private String password;
    private String knownHosts;
}
