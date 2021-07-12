package com.nate.upload.config;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.nate.upload.config.properties.SFTPProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SFTPBinder {

    private final SFTPProperties properties;

    @Autowired
    public SFTPBinder(SFTPProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ChannelSftp getSftpBinder() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(properties.getKnownHosts());

        Session jschSession = jsch.getSession(properties.getUserName(), properties.getRemoteHost());
        jschSession.setPassword(properties.getPassword());
        jschSession.connect();

        ChannelSftp channelSftp = (ChannelSftp) jschSession.openChannel("sftp");
        channelSftp.connect();
        return channelSftp;
    }
}
