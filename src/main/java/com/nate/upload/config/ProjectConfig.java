package com.nate.upload.config;

import com.jcraft.jsch.JSchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class ProjectConfig {

    private final SFTPBinder binder;

    @Autowired
    public ProjectConfig(SFTPBinder binder) {
        this.binder = binder;
    }

    @PreDestroy
    public void shutdownContext() throws JSchException {
        binder.getSftpBinder().exit();
    }
}
