package com.nate.upload.service;

import com.jcraft.jsch.JSchException;
import com.nate.upload.exception.CustomFileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SftpFileUploadService {
    void uploadFiles(List<MultipartFile> files) throws JSchException, CustomFileException;
}
