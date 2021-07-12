package com.nate.upload.service.impl;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.nate.upload.config.SFTPBinder;
import com.nate.upload.config.properties.SFTPProperties;
import com.nate.upload.constants.ProjectConstants;
import com.nate.upload.exception.CustomFileException;
import com.nate.upload.factory.ExceptionFactory;
import com.nate.upload.service.SftpFileUploadService;
import com.nate.upload.util.ProjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.nate.upload.constants.ProjectConstants.FILE_PROCESSING_ERROR;

@Service
@Slf4j
public class FileUploadServiceImpl implements SftpFileUploadService {

    private final SFTPBinder binder;
    private final SFTPProperties properties;

    @Autowired
    public FileUploadServiceImpl(SFTPBinder binder, SFTPProperties properties) {
        this.binder = binder;
        this.properties = properties;
    }

    @Override
    public void uploadFiles(List<MultipartFile> files) throws JSchException, CustomFileException {
        log.info("Inside FileUploadServiceImpl.uploadFiles() --> files: {}", ProjectUtils.getFileNames(files));

        ChannelSftp channelSftp = binder.getSftpBinder();
        List<CustomFileException> exceptions = new ArrayList<>();

        files.forEach(file -> processFile(channelSftp, file, exceptions));

        if(!CollectionUtils.isEmpty(exceptions)) {
            throw exceptions.get(0);
        }
    }

    private void processFile(ChannelSftp channelSftp, MultipartFile file, List<CustomFileException> exceptions) {
        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes())) {
            channelSftp.put(inputStream, file.getOriginalFilename(), ChannelSftp.OVERWRITE);
        } catch (SftpException | IOException e) {
            log.error(ProjectConstants.ERROR_PREFIX + e.getMessage());
            exceptions.add(ExceptionFactory.createCustomFileException(FILE_PROCESSING_ERROR));
        }
    }
}
