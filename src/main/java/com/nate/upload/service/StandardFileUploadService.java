package com.nate.upload.service;

import com.nate.upload.exception.CustomFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StandardFileUploadService {
    void uploadFiles(List<MultipartFile> files) throws IOException, CustomFileException;
}
